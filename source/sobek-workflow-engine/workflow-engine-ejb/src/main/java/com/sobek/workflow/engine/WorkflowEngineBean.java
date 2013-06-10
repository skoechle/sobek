package com.sobek.workflow.engine;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;

import com.sobek.client.operation.OperationMessage;
import com.sobek.client.operation.request.OperationRequestMessage;
import com.sobek.client.operation.status.OperationCompletionMessage;
import com.sobek.client.operation.status.OperationStatusMessage;
import com.sobek.common.result.Result;
import com.sobek.common.util.SystemProperties;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.workflow.WorkflowLocal;
import com.sobek.workflow.entity.WorkflowEntity;
import com.sobek.workflow.result.CreateWorkflowResult;
import com.sobek.workflow.result.StartOperationResult;
import com.sobek.workflow.result.StartWorkflowResult;

@Stateless
public class WorkflowEngineBean implements WorkflowEngineLocal, WorkflowEngineRemote {

	private static final long serialVersionUID = 1L;

	private static final String LOGGER_NAME = WorkflowEngineBean.class.getPackage().getName();
	private static Logger logger = Logger.getLogger(LOGGER_NAME);

	@Resource
	private EJBContext context;
	
	@EJB
	private WorkflowLocal workflow;
	
	@Resource(mappedName="jms/SobekConnectionFactoryJNDIName")
	private QueueConnectionFactory queueConnectionFactory;
	
	private QueueConnection queueConnection;
	private Session session;
	
	@PostConstruct
	private void createJMSObjects() {
		try {
			this.queueConnection = this.queueConnectionFactory.createQueueConnection();
			this.session = this.queueConnection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			logger.log(Level.SEVERE, "Unable to create the JMS resources, an exception was thrown.", e);
		}
	}
	
	@PreDestroy
	private void closeJMSObjects() {
		if(this.session != null) {
			try {
				this.session.close();
			} catch(Exception e) {
				logger.log(Level.WARNING, "An exception was thrown while attempting to close the session.", e);
			}
		}

		if(this.queueConnection != null) {
			try {
				this.queueConnection.close();
			} catch(Exception e) {
				logger.log(Level.WARNING, "An exception was thrown while attempting to close the queue connection.", e);
			}
		}
	}

	@Override
	public Result startWorkflow(String name, Serializable parameters) {
		logger.log(Level.FINEST, "Entering, name = [{0}], parameters = [{1}]", new Object[] {name, parameters});


		if(name == null || name.isEmpty() || parameters == null) {
			throw new IllegalArgumentException(
					"A workflow cannot be started without null or empty values.  " +
					"The given values were:" + SystemProperties.NEW_LINE +
					"Name: [" + name + "]" + SystemProperties.NEW_LINE +
					"Parameters: [" + parameters + "]" + SystemProperties.NEW_LINE);
		}

		CreateWorkflowResult createResult = this.workflow.create(name, parameters);
		Result returnValue = createResult;

		if(createResult.successful()) {
			WorkflowEntity entity = createResult.getEntity();
			StartWorkflowResult startResult = new StartWorkflowResult(entity);

			try {
				startResult = this.workflow.start(entity);
			} catch (Exception e) {
				startResult.exceptionOccurred();
				logger.log(
						Level.SEVERE,
						"An exception was throw while attempting to start the " +
						"workflow for name [" + name + "] with parameters [" + 
						parameters + "].",
						e);
			}
			
			if(startResult.successful()) {
				if(startResult.getOperations().size() > 0) {
					returnValue = startOperations(entity, parameters, startResult.getOperations());
				} else {
					startResult.noOperationsToRun();
					returnValue = startResult;
				}
			} else {
				returnValue = startResult;
			}
		}

		return returnValue;
	}

	@Override
	public void receiveOperationStatus(OperationStatusMessage status) {
		logger.log(Level.INFO, "Handling status message: [{0}]", status);
		this.workflow.updateOperation(status);
	}

	@Override
	public void receiveOperationCompletion(OperationCompletionMessage completion) {
		logger.log(Level.INFO, "Handling completion message: [{0}]", completion);
		List<OperationEntity> operations = this.workflow.completeOperation(completion);
		if(operations != null && !operations.isEmpty()) {
			logger.log(Level.FINEST, "Ther are new operations to start: [{0}]", operations);
			WorkflowEntity entity = this.workflow.find(completion.getWorkflowId());
			this.startOperations(entity, completion.getMaterialValue(), operations);
		}
	}

	@Override
	public void handleUnsupportedOperationMessage(OperationMessage operationMessage) {
		logger.log(Level.INFO, "Handling unsupported message: [{0}]", operationMessage);
		// TODO: handle
	}

	@Override
	public void handleUnsupportedObjectType(Serializable object) {
		logger.log(Level.INFO, "Handling unsupported object type: [{0}]", object);
		// TODO: handle
	}

	@Override
	public void logMessageHandlingException(Message message, Exception e) {
		logger.log(Level.SEVERE, "An exception was thrown while attempting to handle message : [" + message + "]", e);
		// TODO: handle
	}

	private StartOperationResult startOperations(
			WorkflowEntity workflow,
			Serializable material,
			List<OperationEntity> operations) {
		logger.log(Level.FINEST, "Entering, workflow = [{0}], material = [{1}], operations = [{2}]", new Object[] {workflow, material, operations});
		StartOperationResult result = new StartOperationResult(workflow, material);
		for(OperationEntity operation : operations) {
			logger.log(Level.FINEST, "Sending operation message for operation [{0}]", operation);
			try {
				this.sendOperationMessage(workflow, operation, material);
			} catch (JMSException e) {
				result.exceptionOccurred(operation);
				String details =
						"An exception was throw while attempting to send " +
						"the start message for operation [" + operation +
						"] for workflow [" + workflow.getName() + "] with " +
						"parameters [" + material + "].";
				logger.log(Level.SEVERE, details, e);
				try {
					this.workflow.failOperation(workflow, operation, details);
				} catch (Exception failException) {
					logger.log(
							Level.SEVERE,
							"An exception was throw while attempting to fail " +
							"operation [" + operation + "] for workflow [" + 
							workflow.getName() + "] with parameters [" + 
							material + "].",
							failException);
				}
			}
		}
		logger.log(Level.FINEST, "Exiting, returning [{0}]", result);
		return result;
	}

	private void sendOperationMessage(WorkflowEntity workflow, OperationEntity operation, Serializable parameters) throws JMSException {
		if(this.session == null) {
			// Try to create the JMS objects.
			this.createJMSObjects();
			// If the session is still null throw an exception.  There is
			// something seriously wrong with the system.
			if(this.session == null) {
				throw new IllegalStateException(
						"Unable to initialize the JMS session.  There should be " +
						"an exception, in this log, related to the initialization failure.");
			}
		}
		
		OperationRequestMessage request = new OperationRequestMessage(workflow.getId(), operation.getId(), parameters);

		Queue queue = this.session.createQueue(operation.getMessageQueueName());
		ObjectMessage message = this.session.createObjectMessage(request);
		MessageProducer producer = this.session.createProducer(queue);
		producer.send(message);
		producer.close();
	}
}

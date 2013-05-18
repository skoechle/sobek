package com.sobek.workflow.engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
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
import com.sobek.client.operation.status.OperationCompletionMessage;
import com.sobek.client.operation.status.OperationStatusMessage;
import com.sobek.common.util.SystemProperties;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.workflow.WorkflowLocal;
import com.sobek.workflow.entity.WorkflowEntity;

@Stateless
public class WorkflowEngineBean implements WorkflowEngineLocal, WorkflowEngineRemote {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(WorkflowEngineBean.class.getPackage().getName());

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

	@Override
	public StartWorkflowResult startWorkflow(String name, Serializable parameters) {
		logger.log(Level.FINEST, "Entering, name = [{0}], parameters = [{1}]", new Object[] {name, parameters});
		StartWorkflowResult returnValue = new StartWorkflowResult();
		if(name == null || name.isEmpty() || parameters == null) {
			throw new IllegalArgumentException(
					"A workflow cannot be started without null or empty values.  The given values were:" + SystemProperties.NEW_LINE +
					"Name: [" + name + "]" + SystemProperties.NEW_LINE +
					"Parameters: [" + parameters + "]" + SystemProperties.NEW_LINE);
		}

		WorkflowEntity data = null;

		try {
			data = this.workflow.create(name, parameters);
		} catch (Exception e) {
			returnValue.failedToCreate();
		}
		
		List<OperationEntity> operations = new ArrayList<OperationEntity>();
		try {
			operations = this.workflow.start(data);
		} catch (Exception e) {
			returnValue.failedToStart();
			this.context.setRollbackOnly();
		}
		
		try {
			for(OperationEntity operation : operations) {
				this.sendOperationMessage(operation, parameters);
			}
		} catch (JMSException e) {
			returnValue.failedToStart();
			this.context.setRollbackOnly();
		}

		return returnValue;
	}

	private void sendOperationMessage(OperationEntity operation, Serializable parameters) throws JMSException {
		Queue queue = this.session.createQueue(operation.getMessageQueueName());
		ObjectMessage message = this.session.createObjectMessage(parameters);
		MessageProducer producer = this.session.createProducer(queue);
		producer.send(message);
	}

	@Override
	public void receiveStatus(OperationStatusMessage status) {
		logger.log(Level.INFO, "Handling status message: [{0}]", status);
		
		this.workflow.update(status);
	}

	@Override
	public void receiveCompletion(OperationCompletionMessage completion) {
		logger.log(Level.INFO, "Handling completion message: [{0}]", completion);
		this.workflow.complete(completion);
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
}

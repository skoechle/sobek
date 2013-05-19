package com.sobek.workflow.engine.status;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.sobek.client.operation.OperationMessage;
import com.sobek.client.operation.status.OperationCompletionMessage;
import com.sobek.client.operation.status.OperationStatusMessage;
import com.sobek.common.util.SystemProperties;
import com.sobek.workflow.engine.WorkflowEngineLocal;

@MessageDriven(mappedName="jms/SobekStatusQueueJNDIName")
public class OperationStatusMDB implements MessageListener {
	private static final Logger logger = Logger.getLogger(OperationStatusMDB.class.getPackage().getName());

	@EJB
	WorkflowEngineLocal workflowEngine;
	
	@Override
	public void onMessage(Message message) {
		StringBuilder builder = new StringBuilder();
		builder.append(SystemProperties.NEW_LINE);
		builder.append("================================================================================").append(SystemProperties.NEW_LINE);
		builder.append("Received message [" + message + "].").append(SystemProperties.NEW_LINE);
		builder.append("================================================================================").append(SystemProperties.NEW_LINE);
		if(message instanceof TextMessage) {
			try {
				builder.append(((TextMessage)message).getText()).append(SystemProperties.NEW_LINE);
				// TODO: Convert text message into an operation message.  For not just log an
				// error.
				this.workflowEngine.handleUnsupportedObjectType(((TextMessage)message).getText());
			} catch (Exception e) {
				this.workflowEngine.logMessageHandlingException(message, e);
			}
		} else if (message instanceof ObjectMessage) {
			builder.append("Message was an object message.").append(SystemProperties.NEW_LINE);
			try {
				Serializable object = ((ObjectMessage)message).getObject();
				builder.append("Object was type [");
				builder.append(object.getClass().getName());
				builder.append("].").append(SystemProperties.NEW_LINE);
				if(object instanceof OperationStatusMessage) {
					OperationStatusMessage status = (OperationStatusMessage)object;
					this.workflowEngine.receiveOperationStatus(status);
				} else if(object instanceof OperationCompletionMessage) {
					OperationCompletionMessage completion = (OperationCompletionMessage)object;
					this.workflowEngine.receiveOperationCompletion(completion);
				} else if(object instanceof OperationMessage) {
					OperationMessage operationMessage = (OperationMessage)object;
					this.workflowEngine.handleUnsupportedOperationMessage(operationMessage);
				} else {
					this.workflowEngine.handleUnsupportedObjectType(object);
				}
			} catch (Exception e) {
				this.workflowEngine.logMessageHandlingException(message, e);
			}
		} else {
			builder.append("Message was not a text message").append(SystemProperties.NEW_LINE);
		}
		builder.append("================================================================================").append(SystemProperties.NEW_LINE);
		builder.append("================================================================================").append(SystemProperties.NEW_LINE);
		
		
		logger.finest(builder.toString());
	}

}

package com.test.operation.shortrunning;

import java.util.logging.Logger;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import com.sobek.client.operation.OperationClient;
import com.sobek.client.operation.request.OperationRequestMessage;
import com.sobek.client.operation.status.CompletionState;
import com.sobek.client.operation.status.OperationStatus;
import com.sobek.common.util.SystemProperties;

@MessageDriven(mappedName="jms/ShortRunningOperationQueueJNDIName")
public class ShortRunningOperationMDB implements MessageListener {

	private static final Logger logger = Logger.getLogger(ShortRunningOperationMDB.class.getPackage().getName());

	@Override
	public void onMessage(Message message) {
		StringBuilder builder = new StringBuilder();
		OperationRequestMessage request = null;
		builder.append("================================================================================").append(SystemProperties.NEW_LINE);
		builder.append("Received message [" + message + "].").append(SystemProperties.NEW_LINE);
		builder.append("================================================================================").append(SystemProperties.NEW_LINE);
		if(message instanceof TextMessage) {
			try {
				builder.append(((TextMessage)message).getText()).append(SystemProperties.NEW_LINE);
				// TODO: Convert text message into an operation status.  For not just log an
				// error.
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if (message instanceof ObjectMessage) {
			try {
				Object object = ((ObjectMessage)message).getObject();
				if(object instanceof OperationRequestMessage) {
					request = (OperationRequestMessage)object;
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else {
			builder.append("Message was not a text message").append(SystemProperties.NEW_LINE);
		}
		builder.append("================================================================================").append(SystemProperties.NEW_LINE);
		builder.append("================================================================================").append(SystemProperties.NEW_LINE);
		
		logger.finest(builder.toString());
		
		OperationClient client = new OperationClient(request);
		
		client.sendStatusMessage(.25F, OperationStatus.WORKING, "Some randome details for 25% complete.");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		client.sendStatusMessage(.5F, OperationStatus.WORKING, "Some randome details for 50% complete.");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		client.sendStatusMessage(.75F, OperationStatus.WORKING, "Some randome details for 75% complete.");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		client.sendCompletionMessage("Returned material... for now just using a string.  Later we will do something cool.", CompletionState.COMPLETE, "Some randome details for 75% complete.");
	}

}

package com.sobek.client.operation;

import java.io.Serializable;
import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sobek.client.operation.status.OperationCompletionMessage;
import com.sobek.client.operation.status.OperationStatusMessage;
import com.sobek.client.operation.status.Status;
import com.sobek.common.util.SystemProperties;

// TODO: Write this class properly.  It is just hacked for testing
// right now.
public class OperationClient {

	// TODO: Get these from config.
    private static String BINDINGS_FILE_LOCATION = "file:///C:/bindings/sobek";
    private static String CONNECTION_FACTORY_LOOKUP_NAME = "SobekQueueConnectionFactory";
    private static String QUEUE_LOOKUP_NAME = "SobekStatusQueueJNDIName";

    private ConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private Queue queue = null;
    private MessageProducer messageProducer = null;
    
    private OperationMessage message = null;
    
	
	public OperationClient(OperationMessage message) {

		if(message == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values was passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Message (cannot be null): " + message + SystemProperties.NEW_LINE);
		}
		
		this.message = message;
		
        Hashtable<String, String> properties = new Hashtable<String, String>();
        // For use with the File System JNDI Service Provider
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        properties.put(Context.PROVIDER_URL, BINDINGS_FILE_LOCATION);
        
        Context context = null;
        
        try {
			context = new InitialContext(properties);
			this.connectionFactory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY_LOOKUP_NAME);
			this.queue = (Queue) context.lookup(QUEUE_LOOKUP_NAME);
			
			try {
				this.connection = this.connectionFactory.createConnection();
				this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				this.messageProducer = this.session.createProducer(this.queue);
				this.connection.start();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void sendStatusMessage(
		float percentComplete,
		Status status,
		String details)
	{
		OperationStatusMessage messageToSend =
				new OperationStatusMessage(
						this.message.getWorkflowId(),
						this.message.getOperationId(),
						percentComplete,
						status,
						details);
		
        try {
			ObjectMessage objectMessage = session.createObjectMessage(messageToSend);
			this.messageProducer.send(objectMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
	
	public void sendCompletionMessage(
		Status status,
		String details,
		Serializable material)
	{
		OperationCompletionMessage messageToSend =
				new OperationCompletionMessage(
						this.message.getWorkflowId(),
						this.message.getOperationId(),
						status,
						details,
						material);
		
        try {
			ObjectMessage objectMessage = session.createObjectMessage(messageToSend);
			this.messageProducer.send(objectMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}

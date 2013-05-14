package com.sobek.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
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

import com.sobek.client.operation.request.OperationRequestMessage;
import com.sobek.pgraph.operation.Operation;
import com.sobek.pgraph.operation.OperationState;
import com.sobek.workflow.engine.entity.WorkflowData;

@Stateless
public class WorkflowBean implements WorkflowLocal, WorkflowRemote {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Workflow.class.getPackage().getName());
	
	@Resource(mappedName="jms/SobekConnectionFactoryJNDIName")
	private ConnectionFactory connectionFactory;

	@Override
	public boolean create(WorkflowData data) {
		logger.log(
				Level.FINEST,
				"Creating a new workflow for data [{0}].",
				data);
		
		if(data == null) {
			throw new IllegalArgumentException(
					"The create method on class " + this.getClass().getName() +
					" cannot be called with null workflow data.");
		}
		
		return true;
	}

	@Override
	public List<Operation> start(WorkflowData data) {
		logger.log(
				Level.FINEST,
				"Starting workflow for data [{0}].",
				data);
		
		if(data == null) {
			throw new IllegalArgumentException(
					"The start method on class " + this.getClass().getName() +
					" cannot be called with null workflow data.");
		}
		
		Operation operation = new Operation() {
			
			@Override
			public void persist() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getJndiName() {
				// TODO Auto-generated method stub
				return "Operation Name";
			}
			
			@Override
			public OperationState getState() {
				// TODO Auto-generated method stub
				return OperationState.WORKING;
			}
		};
		List<Operation> list = new ArrayList<Operation>();
		list.add(operation);
		
		String queueName = "jms/ShortRunningOperationQueueJNDIName";
		
		Connection connection;
		try {
			connection = this.connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Context context = new InitialContext();
			Queue queue = (Queue) context.lookup(queueName);
			
			OperationRequestMessage request = new OperationRequestMessage(data.getId(), 1234, "junk");
			
			ObjectMessage messageToSend = session.createObjectMessage(request);
			MessageProducer producer = session.createProducer(queue);
			
			producer.send(messageToSend);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

}

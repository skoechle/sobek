package com.sobek.workflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.sobek.client.operation.status.OperationCompletionMessage;
import com.sobek.client.operation.status.OperationStatusMessage;
import com.sobek.pgraph.OperationState;
import com.sobek.pgraph.PgraphManagerLocal;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.workflow.entity.WorkflowEntity;

@Stateless
public class WorkflowBean implements WorkflowLocal, WorkflowRemote {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Workflow.class.getPackage().getName());

	@EJB
	private WorkflowDAOLocal dao;
	
	@EJB
	private PgraphManagerLocal pgraph;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public WorkflowEntity create(String name, Serializable parameters) {
		logger.log(
				Level.FINEST,
				"Creating a new workflow for data for name [{0}] and parameters [{1}].",
				new Object[] {name, parameters});

		WorkflowEntity data = this.dao.create(name, parameters);

		
		return data;
	}

	@Override
	public List<OperationEntity> start(WorkflowEntity data) {
		logger.log(
				Level.FINEST,
				"Starting workflow for data [{0}].",
				data);
		
		if(data == null) {
			throw new IllegalArgumentException(
					"The start method on class " + this.getClass().getName() +
					" cannot be called with null workflow data.");
		}
		
		OperationEntity operation = new OperationEntity(3, "jms/ShortRunningOperationQueueJNDIName", OperationState.WORKING);
		List<OperationEntity> list = new ArrayList<OperationEntity>();
		list.add(operation);
		
		return list;
	}

	@Override
	public void update(OperationStatusMessage status) {
		WorkflowEntity entity = this.dao.getWorkflow(status.getWorkflowId());
		
		this.pgraph.updateOperation(entity.getPGraphId(), status.getOperationId(), status.getPercentComplete(), status.getStatus().toString());
	}

	@Override
	public void complete(OperationCompletionMessage completion) {
		// TODO Auto-generated method stub
		
	}

}

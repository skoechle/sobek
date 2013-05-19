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
import com.sobek.pgraph.InvalidPgraphStructureException;
import com.sobek.pgraph.PgraphManagerLocal;
import com.sobek.pgraph.PgraphState;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.workflow.entity.WorkflowConfigurationEntity;
import com.sobek.workflow.entity.WorkflowEntity;
import com.sobek.workflow.error.CreateWorkflowResult;

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
	public CreateWorkflowResult create(String name, Serializable parameters) {
		logger.log(
				Level.FINEST,
				"Creating a new workflow for data for name [{0}] and parameters [{1}].",
				new Object[] {name, parameters});

		CreateWorkflowResult result = new CreateWorkflowResult(name, parameters); 

		try
		{
			WorkflowConfigurationEntity configEntity = this.dao.findConfiguration(name);
	
			if(configEntity != null) {
				try {
					this.pgraph.createPgraph(configEntity.getPgraph().getEdges());
				} catch (InvalidPgraphStructureException e) {
					result.invlidConfiguration();
				}
	
				WorkflowEntity data = this.dao.create(name, parameters);
				if(data == null) {
					result.creationFailed();
				}
			} else {
				result.configurationDoesNotExist();
			}
		} catch (Exception e) {
			result.exceptionOccurred();
			logger.log(Level.SEVERE, "An exception was thrown while creating the workflow.", e);
		}
		
		logger.log(Level.FINEST, "Exiting, returning [{0}].", result);
		return result;
	}

	@Override
	public List<OperationEntity> start(WorkflowEntity entity) {
		logger.log(
				Level.FINEST,
				"Starting workflow for data [{0}].",
				entity);

		List<OperationEntity> list =
				this.pgraph.start(entity.getPGraphId(), entity.getParameters());
		
		logger.log(
				Level.FINEST,
				"Exiting, returning [{0}].",
				list);

		return list;
	}

	@Override
	public void updateOperation(OperationStatusMessage status) {
		WorkflowEntity entity = this.dao.getWorkflow(status.getWorkflowId());
		
		this.pgraph.updateOperation(entity.getPGraphId(), status.getOperationId(), status.getPercentComplete(), status.getStatus().name());
	}

	@Override
	public List<OperationEntity> completeOperation(OperationCompletionMessage completion) {
		WorkflowEntity entity = this.dao.getWorkflow(completion.getWorkflowId());
		List<OperationEntity> returnValue = new ArrayList<OperationEntity>();

		List<OperationEntity> operationList =
				this.pgraph.completeOperation(
						entity.getPGraphId(),
						completion.getOperationId(),
						completion.getMaterial(),
						completion.getState().name());

		if(operationList == null || operationList.isEmpty()) {
			PgraphState pgraphState = this.pgraph.getState(entity.getPGraphId());
			switch(pgraphState) {
			case CANCELED:
				entity.setStatus(WorkflowState.CANCELED);
				this.dao.update(entity);
				break;
			case COMPLETE:
				entity.setStatus(WorkflowState.COMPLETE);
				this.dao.update(entity);
				break;
			case FAILED:
				entity.setStatus(WorkflowState.FAILED);
				this.dao.update(entity);
				break;
			default:
				// Do nothing, the pgraph is not complete.
				break;
			}
		} else {
			returnValue = operationList;
		}
		return returnValue;
	}

	@Override
	public boolean registrerWorkflowConfiguration(WorkflowConfiguration config) {
		boolean returnValue = false;
		if(config != null) {
			WorkflowConfigurationEntity entity = this.dao.findConfiguration(config.getName());
			if(entity != null) {
				logger.log(
						Level.FINEST,
						"Replacing pgraph for workflow [{0}].",
						config.getName());
				entity.setPgraph(config.getPgraph());
				this.dao.update(entity);
			} else {
				logger.log(
						Level.FINEST,
						"Creating new workflow with name = [{0}].",
						config.getName());
			}
		} else {
			logger.log(
					Level.WARNING,
					"An attempt was made to register a null configuration " +
					"object : [{0}].  The registration request will be ignored.",
					config);
		}
		return returnValue;
	}

	@Override
	public void failOperation(WorkflowEntity entity, OperationEntity operation,
			String details) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void failOperation(WorkflowEntity entity, OperationEntity operation, String details) {
//	}

}

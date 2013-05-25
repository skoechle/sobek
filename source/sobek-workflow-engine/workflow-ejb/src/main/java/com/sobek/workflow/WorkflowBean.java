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

import com.sobek.client.operation.status.CompletionState;
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
	
			if(configEntity != null)
			{
				long pgraphId = 0;
				try {
					pgraphId = this.pgraph.createPgraph(configEntity.getPgraph().getEdges());
				} catch (InvalidPgraphStructureException e) {
					logger.log(Level.SEVERE, "An exception was thrown while creating the pgraph.", e);
					result.invlidConfiguration();
				}
	
				if(result.successful()) {
					WorkflowEntity entity = this.dao.create(name, parameters, pgraphId);
					if(entity == null) {
						result.creationFailed();
					} else {
						// Create a result with the entity so that the entity
						// is returned with the result.
						result = new CreateWorkflowResult(entity);
					}
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
				this.pgraph.start(entity.getPgraphId(), entity.getParameters());
		
		logger.log(
				Level.FINEST,
				"Exiting, returning [{0}].",
				list);

		return list;
	}

	@Override
	public void updateOperation(OperationStatusMessage status) {
		if(status != null) {
			WorkflowEntity entity = this.dao.getWorkflow(status.getWorkflowId());
			if(entity != null) {
				this.pgraph.updateOperation(entity.getPgraphId(), status.getOperationId(), status.getPercentComplete(), status.getStatus().name());
			} else {
				logger.log(
						Level.WARNING,
						"No workflow was found for the workflow ID [{0}] that " +
						"was specified in the status message, ignoring the call.  " +
						"No status update will be made.",
						status.getWorkflowId());
			}
		} else {
			logger.log(
					Level.WARNING,
					"A null message was passed in, ignoring the call.  No " +
					"status update will be made.");
		}
	}

	@Override
	public List<OperationEntity> completeOperation(OperationCompletionMessage completion) {
		if(completion == null) {
			throw new IllegalArgumentException(
					"The given operation competion message was null, no " +
					"operation will be completed.");
		}

		List<OperationEntity> returnValue = new ArrayList<OperationEntity>();
		
		WorkflowEntity entity = this.dao.getWorkflow(completion.getWorkflowId());
		
		if(entity != null) {
			List<OperationEntity> operationList =
					this.pgraph.completeOperation(
							entity.getPgraphId(),
							completion.getOperationId(),
							completion.getMaterial(),
							completion.getState().name());

			if(operationList == null || operationList.isEmpty()) {
				PgraphState pgraphState = this.pgraph.getState(entity.getPgraphId());
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
		} else {
			logger.log(
					Level.WARNING,
					"No workflow was found for the workflow ID [{0}] that " +
					"was specified in the completion message, ignoring the call.  " +
					"No operation will be completed.",
					completion.getWorkflowId());
		}
		return returnValue;
	}

	@Override
	public boolean registrerWorkflow(WorkflowConfiguration config) {
		boolean returnValue = false;
		if(config != null) {
			WorkflowConfigurationEntity entity = this.dao.findConfiguration(config.getName());
			if(entity == null) {
				logger.log(
						Level.FINEST,
						"Replacing pgraph for workflow [{0}].",
						config.getName());
				this.dao.create(config);
				returnValue = true;
			} else {
				logger.log(
						Level.WARNING,
						"An attempt was made to register a configuration " +
						"object [{0}] that already exists.  The registration " +
						"request will be ignored.",
						config);
			}
		} else {
			logger.log(
					Level.WARNING,
					"An attempt was made to register a null configuration " +
					"object [{0}].  The registration request will be ignored.",
					config);
		}
		return returnValue;
	}

	@Override
	public boolean updateWorkflow(WorkflowConfiguration config) {
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
						Level.WARNING,
						"An attempt was made to update a configuration object " +
						"[{0}] that does not exist.  The update request will " +
						"be ignored.",
						config);
			}
		} else {
			logger.log(
					Level.WARNING,
					"An attempt was made to update a null configuration " +
					"object [{0}].  The registration request will be ignored.",
					config);
		}
		return returnValue;
	}

	@Override
	public void failOperation(
			WorkflowEntity entity,
			OperationEntity operation,
			String details)
	{
		this.pgraph.failOperation(entity.getPgraphId(), operation.getId(), CompletionState.FAILED.name());
	}

	@Override
	public WorkflowEntity find(long workflowId) {
		return this.dao.getWorkflow(workflowId);
	}
}

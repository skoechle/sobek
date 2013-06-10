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
import com.sobek.client.operation.status.OperationStatus;
import com.sobek.client.operation.status.OperationStatusMessage;
import com.sobek.pgraph.InvalidPgraphStructureException;
import com.sobek.pgraph.NoSuchMaterialException;
import com.sobek.pgraph.NoSuchOperationException;
import com.sobek.pgraph.OperationState;
import com.sobek.pgraph.PgraphManagerLocal;
import com.sobek.pgraph.PgraphState;
import com.sobek.pgraph.definition.PgraphDefinitionManagerLocal;
import com.sobek.pgraph.definition.entity.PgraphDefinition;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.workflow.entity.WorkflowEntity;
import com.sobek.workflow.result.CreateWorkflowResult;
import com.sobek.workflow.result.StartWorkflowResult;

@Stateless
public class WorkflowBean implements WorkflowLocal, WorkflowRemote {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Workflow.class.getPackage().getName());

	@EJB
	private WorkflowDAOLocal dao;
	
	@EJB
	private PgraphDefinitionManagerLocal definitionManager;
	
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
			PgraphDefinition definition = this.definitionManager.find(name);
	
			if(definition != null)
			{
				long pgraphId = 0;
				try {
					pgraphId = this.pgraph.create(definition);
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
	public StartWorkflowResult start(WorkflowEntity entity) {
		logger.log(
				Level.FINEST,
				"Starting workflow for data [{0}].",
				entity);

		StartWorkflowResult returnValue = null;

		List<OperationEntity> list = new ArrayList<OperationEntity>();
		try {
			list = this.pgraph.start(entity.getPgraphId(), entity.getRawMaterial());
			returnValue = new StartWorkflowResult(entity, list);
		} catch (Exception e) {
			returnValue = new StartWorkflowResult(entity);
			returnValue.exceptionOccurred();
			logger.log(Level.SEVERE, "An exception occured while attempting to start the pgraph.", e);
		}
		
		logger.log(
				Level.FINEST,
				"Exiting, returning [{0}].",
				returnValue);

		return returnValue;
	}

	@Override
	public void updateOperation(OperationStatusMessage status) {
		if(status != null) {
			WorkflowEntity entity = this.dao.getWorkflow(status.getWorkflowId());
			if(entity != null) {
				OperationStatus operationStatus = status.getStatus();
				OperationState state = OperationState.valueOf(operationStatus.name());
				try {
					this.pgraph.updateOperation(status.getOperationId(), status.getPercentComplete(), state);
				} catch (NoSuchOperationException e) {
					logger.log(
							Level.WARNING,
							"No operation was found for the operation ID [{0}] that " +
							"was specified in the status message, ignoring the call.  " +
							"No status update will be made.",
							status.getOperationId());
				}
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
			List<OperationEntity> operationList = new ArrayList<OperationEntity>();

			try {
				operationList = this.pgraph.completeOperation(
						completion.getOperationId(),
						completion.getMaterialName(),
						completion.getMaterialValue());
			} catch (NoSuchOperationException e) {
				logger.log(
						Level.WARNING,
						"No operation was found for the operation ID [{0}] that " +
						"was specified in the completion message, ignoring the call.  " +
						"No operation will be completed.",
						completion.getOperationId());
			} catch (NoSuchMaterialException e) {
				logger.log(
						Level.WARNING,
						"The material [{0}] that was specified in the completion " +
						"message was not recognized for operation ID [{1}], " +
						"ignoring the call.  No operation will be completed.",
						new Object[] {completion.getMaterialValue(), completion.getOperationId()});
			}

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
	public void failOperation(
			WorkflowEntity entity,
			OperationEntity operation,
			String details)
	{
		try {
			this.pgraph.failOperation(operation.getId());
		} catch (NoSuchOperationException e) {
			logger.log(Level.SEVERE, "An exception was throw while attempting to fail the operation.  The operation does not exist..", e);
		}
	}

	@Override
	public WorkflowEntity find(long workflowId) {
		return this.dao.getWorkflow(workflowId);
	}
}

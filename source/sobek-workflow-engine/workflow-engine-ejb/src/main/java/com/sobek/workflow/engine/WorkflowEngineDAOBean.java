package com.sobek.workflow.engine;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sobek.pgraph.operation.Operation;
import com.sobek.workflow.engine.entity.OperationData;
import com.sobek.workflow.engine.entity.OperationKey;
import com.sobek.workflow.engine.entity.WorkflowData;

@Stateless
public class WorkflowEngineDAOBean implements WorkflowEngineDAOLocal {
	private static Logger logger =
			Logger.getLogger(WorkflowEngine.class.getPackage().getName());
	@PersistenceContext(name="workflowEnginePU")
	private EntityManager manager;
	
	@Override
	public WorkflowData create(String name, Serializable parameters) {
		logger.log(
				Level.FINEST,
				"Creating a new workflow for name [{0}] and parameter type [{1}].",
				new Object[] {name, parameters});
		
		WorkflowData workflowData = new WorkflowData(name, parameters);
		this.manager.persist(workflowData);
		return workflowData;
	}

	@Override
	public WorkflowData getWorkflow(long id) {
		
		WorkflowData data = this.manager.find(WorkflowData.class, id);
		return data;
	}

	@Override
	public void update(WorkflowData data) {
		this.manager.persist(data);
	}

	@Override
	public void storeOperations(WorkflowData data, List<Operation> operations) {
		for(Operation operation : operations) {
			OperationKey key = new OperationKey(data.getId(), operation.getJndiName());
			OperationData operationData = new OperationData(key, operation.getState());
			
			data.addOperation(operationData);
			operationData.setWorkflow(data);
			
			this.manager.persist(operationData);
		}
	}

}

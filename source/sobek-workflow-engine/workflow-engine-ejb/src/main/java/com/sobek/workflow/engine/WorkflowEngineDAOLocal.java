package com.sobek.workflow.engine;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.sobek.pgraph.operation.Operation;
import com.sobek.workflow.engine.entity.WorkflowData;

@Local
public interface WorkflowEngineDAOLocal {
	WorkflowData create(String name, Serializable parameters);
	
	WorkflowData getWorkflow(long id);

	void update(WorkflowData data);

	void storeOperations(WorkflowData data, List<Operation> operations);
}

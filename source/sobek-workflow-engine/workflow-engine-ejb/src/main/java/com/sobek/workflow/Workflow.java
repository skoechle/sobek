package com.sobek.workflow;

import java.io.Serializable;
import java.util.List;

import com.sobek.pgraph.operation.Operation;
import com.sobek.workflow.engine.entity.WorkflowData;

public interface Workflow extends Serializable {
	boolean create(WorkflowData data);
	List<Operation> start(WorkflowData data);
}

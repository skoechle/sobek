package com.sobek.workflow;

import java.io.Serializable;

import com.sobek.workflow.engine.entity.WorkflowData;

public interface Workflow extends Serializable {
	boolean create(WorkflowData data);
	boolean start(WorkflowData data);
}

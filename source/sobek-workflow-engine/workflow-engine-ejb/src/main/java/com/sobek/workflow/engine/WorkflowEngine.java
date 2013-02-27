package com.sobek.workflow.engine;

import java.io.Serializable;

public interface WorkflowEngine  extends Serializable {
	long startWorkflow(String workflowName, Serializable parameters);
	
	Serializable getParametersForWorkflow(long id);
}

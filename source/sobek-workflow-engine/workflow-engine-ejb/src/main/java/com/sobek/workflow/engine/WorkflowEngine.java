package com.sobek.workflow.engine;

import java.io.Serializable;

public interface WorkflowEngine  extends Serializable {
	StartWorkflowResult startWorkflow(String workflowName, Serializable parameters);
}

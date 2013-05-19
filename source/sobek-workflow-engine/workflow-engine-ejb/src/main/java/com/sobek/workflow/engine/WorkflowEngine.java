package com.sobek.workflow.engine;

import java.io.Serializable;

import com.sobek.common.result.Result;

public interface WorkflowEngine  extends Serializable {
	Result startWorkflow(String workflowName, Serializable parameters);
}

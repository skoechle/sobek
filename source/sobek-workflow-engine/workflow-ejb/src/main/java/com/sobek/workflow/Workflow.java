package com.sobek.workflow;

import java.io.Serializable;

public interface Workflow extends Serializable {
	boolean registrerWorkflow(WorkflowConfiguration config);
	boolean updateWorkflow(WorkflowConfiguration config);
}

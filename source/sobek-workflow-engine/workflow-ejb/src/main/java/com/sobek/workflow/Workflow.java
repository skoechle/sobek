package com.sobek.workflow;

import java.io.Serializable;

public interface Workflow extends Serializable {
	boolean registrerWorkflowConfiguration(WorkflowConfiguration config);
}

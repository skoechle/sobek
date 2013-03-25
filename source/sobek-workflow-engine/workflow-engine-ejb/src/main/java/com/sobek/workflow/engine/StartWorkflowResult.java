package com.sobek.workflow.engine;

import com.sobek.common.result.Result;

public class StartWorkflowResult extends Result{
	
	long workflowId = 0;
	
	public StartWorkflowResult(long workflowId) {
		if(workflowId > 0) {
			this.workflowId = workflowId;
		}
	}
	
	public long getWorkflowId() {
		return this.workflowId;
	}

}

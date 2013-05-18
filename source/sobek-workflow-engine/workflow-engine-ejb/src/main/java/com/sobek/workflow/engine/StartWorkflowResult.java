package com.sobek.workflow.engine;

import com.sobek.common.result.Result;
import com.sobek.workflow.entity.WorkflowEntity;

public class StartWorkflowResult extends Result{
	
	private static final long serialVersionUID = 1L;
	private WorkflowEntity data = null;

	public StartWorkflowResult() {
	}

	public StartWorkflowResult(WorkflowEntity data) {
		this.data = data;
	}
	
	public Long getWorkflowId() {
		Long returnValue = null;
		if(this.data != null) {
			returnValue = data.getId();
		}
		return returnValue;
	}

	public void failedToCreate() {
		this.addErrorCode(StartWorkflowErrorCode.FAILED_TO_CREATE);
	}

	public void failedToStart() {
		this.addErrorCode(StartWorkflowErrorCode.FAILED_TO_START);
	}

	private void addErrorCode(StartWorkflowErrorCode code) {
		StartWorkflowError error = new StartWorkflowError(code, this.data);
		this.addError(error);
	}
}

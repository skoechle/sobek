package com.sobek.workflow.error;

import java.io.Serializable;

import com.sobek.common.result.Result;
import com.sobek.common.util.SystemProperties;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.workflow.entity.WorkflowEntity;

public class StartOperationResult extends Result{
	
	private static final long serialVersionUID = 1L;

	private WorkflowEntity workflow = null;
	private Serializable material = null;
	
	public StartOperationResult(WorkflowEntity workflow, Serializable material) {
		if(workflow == null || material == null) {
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Workflow (cannot be null) :" + workflow + SystemProperties.NEW_LINE +
					"Material (cannot be null) :" + material + SystemProperties.NEW_LINE);
		}
		this.workflow = workflow;
		this.material = material;
	}

	public void exceptionOccurred(OperationEntity operation) {
		this.addErrorCode(StartOperationErrorCode.EXCEPTION_THROWN, operation);
	}

	private void addErrorCode(StartOperationErrorCode code, OperationEntity operation) {
		if(operation == null) {
			throw new IllegalArgumentException(
					"An invalid operation was passed to the " +
					this.getClass().getName() + ".addErrorCode() method.  The given operation " +
					"was:" + SystemProperties.NEW_LINE +
					"Operation (cannot be null) :" + operation + SystemProperties.NEW_LINE);
		}
		StartOperationError error = new StartOperationError(code, this.workflow, operation, this.material);
		this.addError(error);
	}
}

package com.sobek.workflow.engine.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OperationKey {

	@Column(name="WORKFLOW_ID")
	private long workflowId;
	
	@Column(name="OPERATION_NAME")
	private String operationName;
	
	public OperationKey(long workflowId, String operationName) {
		if(operationName == null || operationName.isEmpty()) {
			throw new IllegalArgumentException(
					"An instance of " + this.getClass() + " cannot be created " +
					"with null or empty values.  The given values were: " +
					"operationName [" + operationName + "].");
		}
	}

	public long getWorkflowId() {
		return workflowId;
	}

	public String getOperationName() {
		return operationName;
	}
}

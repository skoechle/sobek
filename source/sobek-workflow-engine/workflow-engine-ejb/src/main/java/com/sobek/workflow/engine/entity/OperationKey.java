package com.sobek.workflow.engine.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OperationKey implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="WORKFLOW_ID")
	private long workflowId;
	
	@Column(name="OPERATION_NAME")
	private String operationName;
	
	// Required by JPA
	@SuppressWarnings("unused")
	private OperationKey() {}
	
	public OperationKey(long workflowId, String operationName) {
		if(operationName == null || operationName.isEmpty()) {
			throw new IllegalArgumentException(
					"An instance of " + this.getClass() + " cannot be created " +
					"with null or empty values.  The given values were: " +
					"operationName [" + operationName + "].");
		}
		
		this.workflowId = workflowId;
		this.operationName = operationName;
	}

	public long getWorkflowId() {
		return workflowId;
	}

	public String getOperationName() {
		return operationName;
	}
}

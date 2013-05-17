package com.sobek.client.operation;

import java.io.Serializable;

import com.sobek.common.util.SystemProperties;

public class OperationMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long workflowId = 0;
	private long operationId = 0;

	public OperationMessage(
			long workflowId,
			long operationId)
	{
		if(workflowId < 1 || operationId < 1)
		{
			throw new IllegalArgumentException(
					"One or more invalid values was passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Workflow Id (must be > 0): " + workflowId + SystemProperties.NEW_LINE +
					"Operation Id (must be > 0): " + operationId + SystemProperties.NEW_LINE);
		}

		this.workflowId = workflowId;
		this.operationId = operationId;
	}
	
	public long getWorkflowId() {
		return workflowId;
	}
	
	public long getOperationId() {
		return operationId;
	}
}

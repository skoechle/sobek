package com.sobek.client.operation;

import java.io.Serializable;

import com.sobek.common.util.SystemProperties;

public class OperationMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long workflowId = 0;
	private long operationId = 0;
	private String details = "";
	protected OperationMessage(
			long workflowId,
			long operationId)
	{
		this(workflowId, operationId, null);
	}

	protected OperationMessage(
			long workflowId,
			long operationId,
			String details)
	{
		if(workflowId < 1 || operationId < 1
				|| (details != null && details.length() > 1024))
		{
			throw new IllegalArgumentException(
					"One or more invalid values was passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Workflow Id (must be > 0): " + workflowId + SystemProperties.NEW_LINE +
					"Operation Id (must be > 0): " + operationId + SystemProperties.NEW_LINE +
					"Details (length must be < 1024) :" + (details != null ? details.length() : details) + SystemProperties.NEW_LINE);
		}

		if(details != null) {
			this.details = details;
		}
		this.workflowId = workflowId;
		this.operationId = operationId;
	}
	
	public long getWorkflowId() {
		return this.workflowId;
	}
	
	public long getOperationId() {
		return this.operationId;
	}
	
	public String getDetails() {
		return this.details;
	}
}

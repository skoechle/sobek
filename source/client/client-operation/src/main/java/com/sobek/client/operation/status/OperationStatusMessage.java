package com.sobek.client.operation.status;

import com.sobek.client.operation.OperationMessage;
import com.sobek.common.util.SystemProperties;


public class OperationStatusMessage extends OperationMessage {

	private static final long serialVersionUID = 1L;

	private float percentComplete = 0;
	private Status status = Status.UNKNOWN;
	// TODO: Consider adding validation to prevent SQL injection when storing.
	private String details = "";
	
	public OperationStatusMessage(
			long workflowId,
			long operationId,
			float percentComplete,
			Status status,
			String details)
	{
		super(workflowId, operationId);

		if(percentComplete < 0 || percentComplete > 1
				|| status == null || Status.UNKNOWN.equals(status)
				|| (details != null && details.length() > 1024))
		{
			throw new IllegalArgumentException(
					"One or more invalid values was passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Percent Complete (must be >= 0 and <= 1) :" + percentComplete + SystemProperties.NEW_LINE +
					"Status (cannot be null or " + Status.UNKNOWN + ") :" + status + SystemProperties.NEW_LINE +
					"Workflow Id (must be > 0) :" + workflowId + SystemProperties.NEW_LINE +
					"Details (length must be < 1024) :" + (details != null ? details.length() : details) + SystemProperties.NEW_LINE);
		}

		this.percentComplete = percentComplete;
		this.status = status;
		
		if(details != null) {
			this.details = details;
		}
	}
	
	public float getPercentComplete() {
		return percentComplete;
	}
	
	public Status getStatus() {
		return status;
	}

	public String getDetails() {
		return details;
	}
}

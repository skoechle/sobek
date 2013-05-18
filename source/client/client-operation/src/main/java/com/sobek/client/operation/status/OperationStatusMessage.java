package com.sobek.client.operation.status;

import com.sobek.client.operation.OperationMessage;
import com.sobek.common.util.SystemProperties;


public class OperationStatusMessage extends OperationMessage {

	private static final long serialVersionUID = 1L;

	private float percentComplete = 0;
	private OperationStatus status = OperationStatus.WORKING;

	public OperationStatusMessage(
			long workflowId,
			long operationId,
			float percentComplete)
	{
		this(workflowId, operationId, percentComplete, OperationStatus.WORKING, null);
	}

	public OperationStatusMessage(
			long workflowId,
			long operationId,
			float percentComplete,
			String details)
	{
		this(workflowId, operationId, percentComplete, OperationStatus.WORKING, details);
	}	

	public OperationStatusMessage(
			long workflowId,
			long operationId,
			float percentComplete,
			OperationStatus status)
	{
		this(workflowId, operationId, percentComplete, status, null);
	}	

	public OperationStatusMessage(
			long workflowId,
			long operationId,
			float percentComplete,
			OperationStatus status,
			String details)
	{
		super(workflowId, operationId, details);

		if(percentComplete < 0 || percentComplete > 1
				|| status == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values was passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Percent Complete (must be >= 0 and <= 1) :" + percentComplete + SystemProperties.NEW_LINE +
					"Status (cannot be null) :" + status + SystemProperties.NEW_LINE +
					"Workflow Id (must be > 0) :" + workflowId + SystemProperties.NEW_LINE);
		}

		this.percentComplete = percentComplete;
		this.status = status;
	}
	
	public float getPercentComplete() {
		return percentComplete;
	}
	
	public OperationStatus getStatus() {
		return status;
	}
}

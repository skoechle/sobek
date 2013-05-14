package com.sobek.client.operation.status;

import java.io.Serializable;


public class OperationCompletionMessage extends OperationStatusMessage {

	private static final long serialVersionUID = 1L;

	private Serializable material;
	
	public OperationCompletionMessage(
			long workflowId,
			long operationId,
			Status status,
			String details,
			Serializable material)
	{
		super(workflowId, operationId, 1L, status, details);
		
		this.material = material;
	}
	
	public boolean hasMaterial() {
		return this.material != null;
	}

	public Serializable getMaterial() {
		return this.material;
	}
}

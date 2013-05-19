package com.sobek.client.operation.status;

import java.io.Serializable;

import com.sobek.client.operation.OperationMessage;
import com.sobek.common.util.SystemProperties;


public class OperationCompletionMessage extends OperationMessage {

	private static final long serialVersionUID = 1L;

	private CompletionState state;
	private Serializable material;
	
	public OperationCompletionMessage(
			long workflowId,
			long operationId,
			Serializable material,
			CompletionState state,
			String details)
	{
		super(workflowId, operationId, details);

		if(material == null || state == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Material (cannot be null) :" + material + SystemProperties.NEW_LINE +
					"State (cannot be null) :" + state + SystemProperties.NEW_LINE);
		}

		this.state = state;
		this.material = material;
	}
	
	public Serializable getMaterial() {
		return this.material;
	}
	
	public CompletionState getState() {
		return this.state;
	}
}

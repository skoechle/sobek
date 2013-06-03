package com.sobek.client.operation.status;

import java.io.Serializable;

import com.sobek.client.operation.OperationMessage;
import com.sobek.common.util.SystemProperties;


public class OperationCompletionMessage extends OperationMessage {

	private static final long serialVersionUID = 1L;

	private CompletionState state;
	private String materialName;
	private Serializable materialValue;
	public OperationCompletionMessage(
			long workflowId,
			long operationId,
			Serializable materialValue,
			CompletionState state,
			String details)
	{
		this(workflowId, operationId, "", materialValue, state, details);
	}

	public OperationCompletionMessage(
			long workflowId,
			long operationId,
			String materialName,
			Serializable materialValue,
			CompletionState state,
			String details)
	{
		super(workflowId, operationId, details);

		if(materialValue == null || state == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Material (cannot be null) :" + materialValue + SystemProperties.NEW_LINE +
					"State (cannot be null) :" + state + SystemProperties.NEW_LINE);
		}
		
		if(materialName != null && !materialName.isEmpty()) {
			this.materialName = materialName;
		} else {
			this.materialName = materialValue.getClass().getSimpleName();
		}

		this.state = state;
		this.materialValue = materialValue;
	}
	
	public String getMaterialName() {
		return this.materialName;
	}

	public Serializable getMaterialValue() {
		return this.materialValue;
	}
	
	public CompletionState getState() {
		return this.state;
	}
}

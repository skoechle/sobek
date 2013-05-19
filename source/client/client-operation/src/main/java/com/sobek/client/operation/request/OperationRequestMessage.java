package com.sobek.client.operation.request;

import java.io.Serializable;

import com.sobek.client.operation.OperationMessage;
import com.sobek.common.util.SystemProperties;

public class OperationRequestMessage extends OperationMessage {
	private static final long serialVersionUID = 1L;
	
	private Serializable material;

	public OperationRequestMessage(long workflowId, long operationId, Serializable material) {
		super(workflowId, operationId);
		if(material == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"material (cannot be null) :" + material + SystemProperties.NEW_LINE);
		}

		this.material = material;
	}

	public Serializable getMaterial() {
		return material;
	}
}

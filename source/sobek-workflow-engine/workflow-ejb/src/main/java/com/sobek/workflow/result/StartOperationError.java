package com.sobek.workflow.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.sobek.common.result.Message;
import com.sobek.common.util.SystemProperties;
import com.sobek.pgraph.Operation;
import com.sobek.workflow.entity.WorkflowEntity;

public class StartOperationError extends Message {

	private static final long serialVersionUID = 1L;
	private final static String WORKFLOW_NAME = "{{WORKFLOW_NAME}}";
	private final static String OPERATION_NAME = "{{OPERATION_NAME}}";
	private final static String MATERIAL = "{{MATERIAL}}";
	
	private Map<String, String> substitutionValues = new HashMap<String, String>();

	protected StartOperationError(StartOperationErrorCode code, WorkflowEntity workflow, Operation operation, Serializable material) {
		super(code);
		
		if(workflow == null || operation == null || material == null) {
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Workflow (cannot be null) :" + workflow + SystemProperties.NEW_LINE +
					"Operation (cannot be null) :" + operation + SystemProperties.NEW_LINE +
					"Material (cannot be null) :" + material + SystemProperties.NEW_LINE);
		}
		
		substitutionValues.put(WORKFLOW_NAME, workflow.getName());
		substitutionValues.put(OPERATION_NAME, operation.getName());
		substitutionValues.put(MATERIAL, material.toString());
	}

	@Override
	protected String getSubstitutionValue(String key) {
		String returnValue = "";
		if(this.substitutionValues.containsKey(key)) {
			returnValue = this.substitutionValues.get(key);
		}
		return returnValue;
	}

}

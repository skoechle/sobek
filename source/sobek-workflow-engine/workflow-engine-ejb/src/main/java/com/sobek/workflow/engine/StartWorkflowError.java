package com.sobek.workflow.engine;

import java.util.HashMap;
import java.util.Map;

import com.sobek.common.result.Message;
import com.sobek.workflow.entity.WorkflowEntity;

public class StartWorkflowError extends Message {

	private static final long serialVersionUID = 1L;
	private final static String WORKFLOW_NAME = "{{WORKFLOW_NAME}}";
	
	private Map<String, String> substitutionValues = new HashMap<String, String>();

	protected StartWorkflowError(StartWorkflowErrorCode code, WorkflowEntity data) {
		super(code);
		
		if(data == null) {
			throw new IllegalArgumentException(
					"An instance of " + this.getClass() + " cannot be created " +
					"with null or empty values.  The given values were: " +
					"WorkflowData [" + data + "].");
		}
		
		substitutionValues.put(WORKFLOW_NAME, data.getName());
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

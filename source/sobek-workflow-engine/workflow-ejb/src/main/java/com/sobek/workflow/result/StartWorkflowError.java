package com.sobek.workflow.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.sobek.common.result.Message;
import com.sobek.common.util.SystemProperties;

public class StartWorkflowError extends Message {

	private static final long serialVersionUID = 1L;
	private final static String WORKFLOW_NAME = "{{WORKFLOW_NAME}}";
	private final static String PARAMETERS = "{{PARAMETERS}}";
	
	private Map<String, String> substitutionValues = new HashMap<String, String>();

	protected StartWorkflowError(StartWorkflowErrorCode code, String name, Serializable parameters) {
		super(code);
		
		if(name == null || name.isEmpty() || parameters == null) {
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Name (cannot be null or empty) :" + name + SystemProperties.NEW_LINE +
					"Parameters (cannot be null) :" + parameters + SystemProperties.NEW_LINE);
		}
		
		substitutionValues.put(WORKFLOW_NAME, name);
		substitutionValues.put(PARAMETERS, parameters.toString());
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

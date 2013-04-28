package com.sobek.common.result;

import java.util.HashMap;
import java.util.Map;

public class PublicMessage extends Message {
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> substitutionValues = new HashMap<String, String>();

	public PublicMessage(MessageCode code) {
		super(code);
	}

	@Override
	protected String getSubstitutionValue(String key) {
		return substitutionValues.get(key);
	}
	
	public void addSubstitutionValue(String key, String value) {
		this.substitutionValues.put(key, value);
	}

}

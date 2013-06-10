package com.sobek.workflow.result;

import java.io.Serializable;

import com.sobek.common.result.Result;
import com.sobek.common.util.SystemProperties;
import com.sobek.workflow.entity.WorkflowEntity;

public class CreateWorkflowResult extends Result{
	
	private static final long serialVersionUID = 1L;
	private String name = "";
	private Serializable parameters = null;
	private WorkflowEntity entity = null;

	public CreateWorkflowResult(WorkflowEntity entity) {
		if(entity == null) {
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Workflow (cannot be null) :" + entity + SystemProperties.NEW_LINE);
		}

		this.name = entity.getName();
		this.parameters = entity.getRawMaterial();
		this.entity = entity;
	}

	public CreateWorkflowResult(String name, Serializable parameters) {
		if(name == null || name.isEmpty() || parameters == null) {
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Name (cannot be null or empty) :" + name + SystemProperties.NEW_LINE +
					"Parameters (cannot be null) :" + parameters + SystemProperties.NEW_LINE);
		}

		this.name = name;
		this.parameters = parameters;
	}
	
	public WorkflowEntity getEntity() {
		return this.entity;
	}
	
	public void exceptionOccurred() {
		this.addErrorCode(CreateWorkflowErrorCode.EXCEPTION_THROWN);
	}

	public void configurationDoesNotExist() {
		this.addErrorCode(CreateWorkflowErrorCode.CONFIGURATION_NOT_FOUND);
	}

	public void invlidConfiguration() {
		this.addErrorCode(CreateWorkflowErrorCode.INVALID_CONFIGURATION);
	}

	public void creationFailed() {
		this.addErrorCode(CreateWorkflowErrorCode.CREATION_FAILED);
	}

	private void addErrorCode(CreateWorkflowErrorCode code) {
		CreateWorkflowError error = new CreateWorkflowError(code, this.name, this.parameters);
		this.addError(error);
	}
}

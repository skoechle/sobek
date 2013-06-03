package com.sobek.workflow.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sobek.common.result.Result;
import com.sobek.common.util.SystemProperties;
import com.sobek.pgraph.Operation;
import com.sobek.workflow.entity.WorkflowEntity;

public class StartWorkflowResult extends Result{
	
	private static final long serialVersionUID = 1L;
	private String name = null;
	private Serializable parameters = null;
	private List<Operation> operations = new ArrayList<Operation>();
	

	public StartWorkflowResult(WorkflowEntity entity) {
		this(entity, new ArrayList<Operation>());
	}

	public StartWorkflowResult(WorkflowEntity entity, List<Operation> list) {
		if(entity == null) {
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Workflow (cannot be null) :" + entity + SystemProperties.NEW_LINE);
		}

		this.name = entity.getName();
		this.parameters = entity.getRawMaterial();
		if(list != null) {
			this.operations.addAll(list);
		}
	}
	
	public StartWorkflowResult(String name, Serializable parameters) {
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

	public String getName() {
		return name;
	}

	public Serializable getParameters() {
		return parameters;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void exceptionOccurred() {
		this.addErrorCode(StartWorkflowErrorCode.EXCEPTION_THROWN);
	}

	public void noOperationsToRun() {
		this.addErrorCode(StartWorkflowErrorCode.NO_OPERATIONS);
	}

	private void addErrorCode(StartWorkflowErrorCode code) {
		StartWorkflowError error = new StartWorkflowError(code, this.name, this.parameters);
		this.addError(error);
	}
}

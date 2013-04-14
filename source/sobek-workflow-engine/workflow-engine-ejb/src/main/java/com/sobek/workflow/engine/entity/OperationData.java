package com.sobek.workflow.engine.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sobek.pgraph.operation.OperationState;

@Entity
@Table(name = "WORKFLOW_OPERATION_DATA")
public class OperationData {
	
	@EmbeddedId
	private OperationKey key;
	
	@Column(name="STATE")
	private String stateValue;
	
	@Transient
	private OperationState state;
	
	@ManyToOne
	@JoinColumns(value = {
			@JoinColumn(name="WORKFLOW_ID", referencedColumnName="ID")
			})
	private WorkflowData workflow;
	
	// Required by JPA
	@SuppressWarnings("unused")
	private OperationData() {}
	
	public OperationData(OperationKey key, OperationState state) {
		if(key == null || state == null) {
			throw new IllegalArgumentException(
					"An instance of " + this.getClass() + " cannot be created " +
					"with null or empty values.  The given values were: " +
					"OperationKey [" + key + "]," +
					"OperationState [" + state + "].");
		}
		
		this.key = key;
		this.state = state;
	}

	public OperationKey getKey() {
		return key;
	}

	public OperationState getState() {
		return state;
	}

	public void setWorkflow(WorkflowData data) {
		this.workflow = data;
	}
	
	@PrePersist
	private void setDatabaseEnumValues() {
		this.stateValue = this.state.name();
	}
	
	@PostLoad
	private void setEnumValues() {
		this.state = OperationState.valueOf(this.stateValue);
	}
}

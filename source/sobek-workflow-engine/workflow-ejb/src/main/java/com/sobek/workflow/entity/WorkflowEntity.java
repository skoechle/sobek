package com.sobek.workflow.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sobek.workflow.WorkflowState;

@Entity
@Table(name="WORKFLOW")
public class WorkflowEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    @SequenceGenerator(name="idGenerator",
            sequenceName="WORKFLOW_ID_GENERATOR",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
         generator="idGenerator")
    @Id
	@Column(name="ID")
	private long id;
	
	@Column(name="NAME")
	private String name;
    
    @Column(name="PGRAPH_ID")
    private long pgraphId;
	
	@Column(name="STATUS")
	private String stateString;
	
	@Transient
	private WorkflowState state;
	
	@Column(name="PARAMETERS")
	@Lob
	private Serializable parameters;

	@SuppressWarnings("unused")
	private WorkflowEntity() {
	}
	
	public WorkflowEntity(String name, Serializable parameters) {
		this.name = name;
		this.parameters = parameters;
		this.state = WorkflowState.NOT_STARTED;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public long getPGraphId() {
		return this.pgraphId;
	}

	public WorkflowState getStatus() {
		return this.state;
	}
	
	public void setStatus(WorkflowState status) {
		this.state = status;
	}

	public Serializable getParameters() {
		return this.parameters;
	}

	public void failed() {
		this.state = WorkflowState.FAILED;
	}
	
	@PrePersist
	private void prePersist() {
		this.stateString = state.name();
	}
	
	@PostLoad
	private void postLoad() {
		this.state = WorkflowState.valueOf(this.stateString);
	}
}

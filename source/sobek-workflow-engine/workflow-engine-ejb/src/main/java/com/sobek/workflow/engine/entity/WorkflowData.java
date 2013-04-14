package com.sobek.workflow.engine.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sobek.workflow.engine.WorkflowStatus;

@Entity
@Table(name="WORKFLOW")
public class WorkflowData implements Serializable {
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
	
	@Column(name="STATUS")
	private String statusString;
	
	@Transient
	private WorkflowStatus status;
	
	@Column(name="PARAMETERS")
	@Lob
	private Serializable parameters;
	
	@OneToMany(mappedBy="workflow")
	private Set<OperationData> operations = new HashSet<OperationData>();

	@SuppressWarnings("unused")
	private WorkflowData() {
	}
	
	public WorkflowData(String name, Serializable parameters) {
		this.name = name;
		this.parameters = parameters;
		this.status = WorkflowStatus.CREATED;
	}

	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public WorkflowStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(WorkflowStatus status) {
		this.status = status;
	}

	public Serializable getParameters() {
		return this.parameters;
	}

	public void failed() {
		this.status = WorkflowStatus.FAILED;
	}
	
	@PrePersist
	private void prePersist() {
		this.statusString = status.name();
	}
	
	@PostLoad
	private void postLoad() {
		this.status = WorkflowStatus.valueOf(this.statusString);
	}

	public void addOperation(OperationData operationData) {
		this.operations.add(operationData);
	}
}

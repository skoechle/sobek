package com.sobek.pgraph.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobek.pgraph.NodeType;
import com.sobek.pgraph.OperationState;

@Entity
@Table(name = "OPERATION")
@DiscriminatorValue("OPERATION")
public class OperationEntity extends NodeEntity {

	@Column(name = "STATE")
	private String state;

	@Column(name = "PERCENT_COMPLETE")
	private float percentComplete = 0f;

	@Column(name = "MESSAGE_QUEUE_NAME")
	private String messageQueueName;

	@SuppressWarnings("unused")
	private OperationEntity() {
		// Required by JPA.
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "EDGE", joinColumns = {
			@JoinColumn(name = "PGRAPH_ID", referencedColumnName = "PGRAPH_ID"),
			@JoinColumn(name = "TO_NODE_ID", referencedColumnName = "ID"), }, inverseJoinColumns = {
			@JoinColumn(name = "PGRAPH_ID", referencedColumnName = "PGRAPH_ID"),
			@JoinColumn(name = "FROM_NODE_ID", referencedColumnName = "ID"), })
	private Set<MaterialEntity> inputMaterials = new HashSet<MaterialEntity>();

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "EDGE", joinColumns = {
			@JoinColumn(name = "PGRAPH_ID", referencedColumnName = "PGRAPH_ID"),
			@JoinColumn(name = "FROM_NODE_ID", referencedColumnName = "ID"), }, inverseJoinColumns = {
			@JoinColumn(name = "PGRAPH_ID", referencedColumnName = "PGRAPH_ID"),
			@JoinColumn(name = "TO_NODE_ID", referencedColumnName = "ID"), })
	private Set<MaterialEntity> outputMaterials = new HashSet<MaterialEntity>();

	public OperationEntity(long pgraphId, String name, String messageQueueName) {
		super(pgraphId, name, NodeType.OPERATION);
		this.messageQueueName = messageQueueName;
		this.state = OperationState.UNEVALUATED.toString();
	}

	public OperationState getState() {
		return OperationState.valueOf(this.state);
	}

	public void setState(OperationState state) {
		this.state = state.toString();
	}

	public float getPercentComplete() {
		return this.percentComplete;
	}

	public String getMessageQueueName() {
		return this.messageQueueName;
	}

	public void setPercentComplete(float percentComplete) {
		this.percentComplete = percentComplete;
	}

	public Set<MaterialEntity> getInputMaterials() {
		return Collections.unmodifiableSet(this.inputMaterials);
	}

	public Set<MaterialEntity> getOutputMaterials() {
		return Collections.unmodifiableSet(this.outputMaterials);
	}
}

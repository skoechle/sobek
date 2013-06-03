package com.sobek.pgraph.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.sobek.pgraph.NodeType;
import com.sobek.pgraph.OperationState;

@Entity
@Table(name = "OPERATION")
@DiscriminatorValue("OPERATION")
public class OperationEntity extends NodeEntity {


	@Column(name = "OPERATION_STATE")
	private String state;

	@Column(name = "PERCENT_COMPLETE")
	private int percentComplete = 0;

	@Column(name = "MESSAGE_QUEUE_NAME")
	private String messageQueueName;

	@SuppressWarnings("unused")
	private OperationEntity() {
		// Required by JPA.
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
		name = "EDGE_MAP",
		joinColumns = {@JoinColumn(referencedColumnName = "ID", name = "TO_NODE_ID")},
		inverseJoinColumns = {@JoinColumn(referencedColumnName = "ID", name = "FROM_NODE_ID")}
	)
	private Set<MaterialEntity> inputMaterials = new HashSet<MaterialEntity>();

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
		name = "EDGE_MAP",
		joinColumns = {@JoinColumn(referencedColumnName = "ID", name = "FROM_NODE_ID")},
		inverseJoinColumns = {@JoinColumn(referencedColumnName = "ID", name = "TO_NODE_ID")}
	)
	private Set<MaterialEntity> outputMaterials = new HashSet<MaterialEntity>();

	public OperationEntity(String name, String messageQueueName) {
		super(name, NodeType.OPERATION);
		this.messageQueueName = messageQueueName;
		this.state = OperationState.UNEVALUATED.toString();
	}

	public OperationState getState() {
		return OperationState.valueOf(this.state);
	}

	public void setState(OperationState state) {
		this.state = state.toString();
	}

	public int getPercentComplete() {
		return this.percentComplete;
	}

	public String getMessageQueueName() {
		return this.messageQueueName;
	}

	public void setPercentComplete(int percentComplete) {
		this.percentComplete = percentComplete;
	}

	public Set<MaterialEntity> getInputMaterials() {
		return Collections.unmodifiableSet(this.inputMaterials);
	}
	
	public void addInputMaterial(MaterialEntity material) {
		material.getDependencies().add(this);
		this.inputMaterials.add(material);
	}

	public Set<MaterialEntity> getOutputMaterials() {
		return Collections.unmodifiableSet(this.outputMaterials);
	}
	
	public void addOutputMaterial(MaterialEntity material) {
		material.getOperations().add(this);
		this.outputMaterials.add(material);
	}

	public boolean hasAllInputs() {
		boolean returnValue = true;
		for(MaterialEntity input : this.inputMaterials) {
			if(!input.available()) {
				returnValue = false;
				break;
			}
		}
		return returnValue;
	}
}

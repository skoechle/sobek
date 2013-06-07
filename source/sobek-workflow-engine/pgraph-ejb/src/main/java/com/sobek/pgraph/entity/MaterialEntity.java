package com.sobek.pgraph.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sobek.pgraph.MaterialState;
import com.sobek.pgraph.NodeType;

@Entity
@Table(name = "MATERIAL")
public class MaterialEntity extends NodeEntity {

	@Column(name = "MATERIAL_STATE")
	private String state;

	@OneToOne(fetch=FetchType.LAZY, mappedBy="material", cascade=CascadeType.ALL)
	private MaterialValueEntity value = null;

	@ManyToMany(mappedBy="inputMaterials", fetch=FetchType.LAZY)
	private Set<OperationEntity> dependencies = new HashSet<OperationEntity>();

	@ManyToMany(mappedBy="outputMaterials", fetch=FetchType.LAZY)
	private Set<OperationEntity> operations = new HashSet<OperationEntity>();

	protected MaterialEntity() {
		// Required because JPA requires children to have a default constructor.
	}

	public MaterialEntity(String name, NodeType type) {
		super(name, type);
		this.state = MaterialState.NOT_AVAILABLE.toString();
	}

	public MaterialState getState() {
		return MaterialState.valueOf(this.state);
	}

	public void setValue(MaterialValueEntity value) {
		if(value != null) {
			this.value = value;
			this.state = MaterialState.AVAILABLE.toString();
		}
	}

	public Serializable getValue() {
		return (this.value != null ? this.value.getValue() : null);
	}

	public Set<OperationEntity> getDependencies() {
		this.dependencies.size();
		return this.dependencies;
	}

	public boolean available() {
		return MaterialState.AVAILABLE.toString().equals(this.state);
	}

	public Set<OperationEntity> getOperations() {
		this.operations.size();
		return this.operations;
	}
}

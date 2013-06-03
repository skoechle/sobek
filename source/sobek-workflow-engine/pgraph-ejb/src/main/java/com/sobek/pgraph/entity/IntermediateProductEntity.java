package com.sobek.pgraph.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sobek.pgraph.NodeType;

@Entity
@Table(name = "MATERIAL")
@DiscriminatorValue("INTERMEDIATE_PRODUCT")
public class IntermediateProductEntity extends MaterialEntity {

	@SuppressWarnings("unused")
	private IntermediateProductEntity() {
		// Required by JPA
	}

	public IntermediateProductEntity(String name) {
		super(name, NodeType.INTERMEDIATE_PRODUCT);
	}
}

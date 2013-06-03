package com.sobek.pgraph.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sobek.pgraph.NodeType;

@Entity
@Table(name = "MATERIAL")
@DiscriminatorValue("RAW_MATERIAL")
public class RawMaterialEntity extends MaterialEntity {

	@SuppressWarnings("unused")
	private RawMaterialEntity() {
		// Required by JPA
	}

	public RawMaterialEntity(String name) {
		super(name, NodeType.RAW_MATERIAL);
	}
}

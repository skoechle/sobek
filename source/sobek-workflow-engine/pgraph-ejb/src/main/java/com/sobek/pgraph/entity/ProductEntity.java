package com.sobek.pgraph.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sobek.pgraph.NodeType;

@Entity
@Table(name = "MATERIAL")
@DiscriminatorValue("PRODUCT")
public class ProductEntity extends MaterialEntity {

	@SuppressWarnings("unused")
	private ProductEntity() {
		// Required by JPA
	}

	public ProductEntity(long pgraphId, String name) {
		super(pgraphId, name, NodeType.PRODUCT);
	}
}

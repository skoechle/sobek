package com.sobek.pgraph.definition.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlType;

import com.sobek.pgraph.NodeType;

@XmlType(namespace = "com.sobek.pgraph.definition")
@Entity
@Table(name="MATERIAL_DEFINITION")
@DiscriminatorValue("PRODUCT")
public class ProductDefinition extends MaterialDefinition{ 
    private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private ProductDefinition() {
		// Required by JAXB and JPA
	}

	public ProductDefinition(String name, Class<?> materialType) {
		super(name, NodeType.PRODUCT, materialType);
	}
}

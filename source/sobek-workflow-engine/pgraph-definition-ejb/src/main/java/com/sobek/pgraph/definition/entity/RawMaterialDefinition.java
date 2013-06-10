package com.sobek.pgraph.definition.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlType;

import com.sobek.pgraph.NodeType;

@XmlType(namespace = "com.sobek.pgraph.definition")
@Entity
@Table(name="MATERIAL_DEFINITION")
@DiscriminatorValue("RAW_MATERIAL")
public class RawMaterialDefinition extends MaterialDefinition{ 
    private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private RawMaterialDefinition() {
		// Required by JAXB and JPA
	}

	public RawMaterialDefinition(String name, Class<?> materialType) {
		super(name, NodeType.RAW_MATERIAL, materialType);
	}
}

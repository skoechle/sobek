package com.sobek.pgraph.definition.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.sobek.common.util.SystemProperties;
import com.sobek.pgraph.NodeType;

@XmlType(namespace = "com.sobek.pgraph.definition")
@Entity
@Table(name="MATERIAL_DEFINITION")
public abstract class MaterialDefinition extends NodeDefinition {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(required = true)
	@Column(name="MATERIAL_TYPE")
	private String materialType;

	protected MaterialDefinition() {
		// Required by JAXB and JPA
	}

	protected MaterialDefinition(String name, NodeType type, Class<?> materialType) {
		super(name, type);
		
		if(materialType == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Material Type (cannot be null) : " + materialType + SystemProperties.NEW_LINE);
		}
		
		this.materialType = materialType.getName();
	}
}

package com.sobek.pgraph.definition.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.sobek.common.util.SystemProperties;
import com.sobek.pgraph.NodeType;

@XmlType(namespace = "com.sobek.pgraph.definition")
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="NODE_TYPE", discriminatorType=DiscriminatorType.STRING)
@Table(name="NODE_DEFINITION")
public abstract class NodeDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(required = true)
	@Id
	@Column(name="NAME")
	private String name;

	@XmlElement(required = true)
	@Column(name = "NODE_TYPE")
	private String type;

	protected NodeDefinition() {
		// Required by JAXB and JPA
	}

	protected NodeDefinition(String name, NodeType type) {
		if(name == null || name.isEmpty()
				|| type == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Name (cannot be null or empty) : " + name + SystemProperties.NEW_LINE +
					"Type (cannot be null) : " + type + SystemProperties.NEW_LINE);
		}


		this.name = name;
		this.type = type.name();
	}

	public String getName() {
		return name;
	}
	
	public NodeType getType() {
		return NodeType.valueOf(this.type);
	}
	
	@Override
	public String toString() {
		return "Name : " + this.name;
	}
}
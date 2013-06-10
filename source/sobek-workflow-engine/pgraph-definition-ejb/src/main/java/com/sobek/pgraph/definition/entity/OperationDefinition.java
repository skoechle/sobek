package com.sobek.pgraph.definition.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.sobek.common.util.SystemProperties;
import com.sobek.pgraph.NodeType;

@XmlType(namespace = "com.sobek.pgraph.definition")
@Entity
@Table(name="OPERATION_DEFINITION")
@DiscriminatorValue("OPERATION")
public class OperationDefinition extends NodeDefinition {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(required = true)
	@Column(name="QUEUE_NAME")
	private String queueName;

	@SuppressWarnings("unused")
	private OperationDefinition() {
		// Required by JAXB and JPA
	}

	public OperationDefinition(String name, String queueName) {
		super(name, NodeType.OPERATION);
		
		if(queueName == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Queue Name (cannot be null or empty) : " + queueName + SystemProperties.NEW_LINE);
		}
		
		this.queueName = queueName;
	}
	
	public String getQueueName() {
		return this.queueName;
	}
}

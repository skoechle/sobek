package com.sobek.pgraph;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "com.sobek.pgraph")
public class Operation extends Node {

	@XmlTransient
	private static final long serialVersionUID = 1L;

	@XmlElement(required = true)
	private String messageQueueName;

	@SuppressWarnings("unused")
	private Operation() {
		// Required by JAXB.
	}

	public Operation(long id, String name, String messageQueueName) {
		super(id, name);
		this.messageQueueName = messageQueueName;
	}

	public final NodeType getNodeType() {
		return NodeType.OPERATION;
	}

	public String getMessageQueueName() {
		return this.messageQueueName;
	}
}

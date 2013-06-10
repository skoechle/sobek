package com.sobek.pgraph.definition.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.sobek.common.util.SystemProperties;

@XmlType(namespace = "com.sobek.pgraph.definition")
@Entity
@Table(name="EDGE_DEFINITION")
public class EdgeDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlTransient
	@Id
	@Column(name="NAME")
	private String name;
	
	@XmlElement(required=true)
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="FROM_NODE_NAME")
	private NodeDefinition fromNode;
	
	@XmlElement(required=true)
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="TO_NODE_NAME")
	private NodeDefinition toNode;
	
	@XmlTransient
	@ManyToOne
	@JoinColumn(name="PGRAPH_NAME")
	private PgraphDefinition pgraph;
	
	@SuppressWarnings("unused")
	private EdgeDefinition() {
		//required by JAXB and JPA
	}
	
	// Constructor is package private.  The only way to create a Edge Definition
	// is to add it to the PGraph Definition.
	EdgeDefinition(PgraphDefinition pgraph, NodeDefinition fromNode, NodeDefinition toNode) {
		if(pgraph == null
				|| fromNode == null
				|| toNode == null)
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"PGraph (cannot be null) : " + pgraph + SystemProperties.NEW_LINE +
					"From Node (cannot be null) : " + fromNode + SystemProperties.NEW_LINE +
					"To Node (cannot be null) : " + toNode + SystemProperties.NEW_LINE);
		}

		this.pgraph = pgraph;
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.name = fromNode.getName() + "|->|" + toNode.getName();
	}

	public String getName() {
		return this.name;
	}

	public NodeDefinition getFromNode() {
		return this.fromNode;
	}

	public NodeDefinition getToNode() {
		return this.toNode;
	}

	public PgraphDefinition getPgraph() {
		return this.pgraph;
	}
}

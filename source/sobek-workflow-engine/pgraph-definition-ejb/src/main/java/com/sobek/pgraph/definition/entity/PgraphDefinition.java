package com.sobek.pgraph.definition.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.sobek.common.util.SystemProperties;

@XmlType(namespace = "com.sobek.pgraph.definition")
@XmlRootElement(namespace = "com.sobek.pgraph.definition")
@Entity
@Table(name="PGRAPH_DEFINITION")
@NamedQueries({
		@NamedQuery(name=PgraphDefinition.GET_DEFINITION_BY_NAME, query="SELECT pgd FROM PgraphDefinition pgd WHERE pgd.name = :" + PgraphDefinition.NAME_PARAMETER)
	})
public class PgraphDefinition implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final Logger logger = Logger.getLogger(PgraphDefinition.class.getPackage().getName());
	public static final String GET_DEFINITION_BY_NAME = "PgraphDefinition.getConfigByName";
	public static final String NAME_PARAMETER = "name";

    @XmlElement(required = true)
	@Id
	@Column(name="NAME")
	private String name = "";
	
	@XmlElement(required = true)
	@ManyToMany
	@JoinTable(
		name="PGRAPH_TO_NODE_DEF_MAP",
		joinColumns={@JoinColumn(name="PGRAPH_NAME")},
		inverseJoinColumns={@JoinColumn(name="NODE_NAME")})
	private final List<NodeDefinition> nodes = new ArrayList<NodeDefinition>();

	@XmlElement(required = true)
	@OneToMany(mappedBy="pgraph", cascade=CascadeType.ALL)
	private final List<EdgeDefinition> edges = new ArrayList<EdgeDefinition>();
	
	@SuppressWarnings("unused")
	private PgraphDefinition() {
		// Required by JPA
	}

	public PgraphDefinition(String name) {
		if(name == null || name.isEmpty())
		{
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " +
					this.getClass().getName() + " constructor.  The given values " +
					"were:" + SystemProperties.NEW_LINE +
					"Name (cannot be null or empty) : " + name + SystemProperties.NEW_LINE);
		}

		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<NodeDefinition> getNodes() {
		return Collections.unmodifiableList(nodes);
	}
	
	public boolean addNode(NodeDefinition nodeToAdd) {
		boolean returnValue = false;
		if(nodeToAdd != null && !this.nodes.contains(nodeToAdd)) {
			returnValue = this.nodes.add(nodeToAdd);
		}
		return returnValue;
	}
	
	public boolean removeNode(NodeDefinition nodeToRemove) {
		boolean returnValue = false;
		if(nodeToRemove != null && this.nodes.contains(nodeToRemove)) {
			returnValue = this.nodes.remove(nodeToRemove);
		}
		return returnValue;
	}

	public List<EdgeDefinition> getEdges() {
		return Collections.unmodifiableList(edges);
	}
	
	public boolean addEdge(RawMaterialDefinition fromNode, OperationDefinition toNode) {
		return this.addTheEdge(fromNode, toNode);
	}

	public boolean addEdge(IntermediateProductDefinition fromNode, OperationDefinition toNode) {
		return this.addTheEdge(fromNode, toNode);
	}

	public boolean addEdge(OperationDefinition fromNode, IntermediateProductDefinition toNode) {
		return this.addTheEdge(fromNode, toNode);
	}

	public boolean addEdge(OperationDefinition fromNode, ProductDefinition toNode) {
		return this.addTheEdge(fromNode, toNode);
	}

	private boolean addTheEdge(NodeDefinition fromNode, NodeDefinition toNode) {
		boolean returnValue = false;
		
		if(fromNode != null && toNode != null) {
			this.addNode(fromNode);
			this.addNode(toNode);
			
			EdgeDefinition edgeToAdd = new EdgeDefinition(this, fromNode, toNode);

			if(!this.edges.contains(edgeToAdd)) {
				returnValue = this.edges.add(edgeToAdd);
			}
		}
		return returnValue;
	}
	
	public boolean removeEdge(EdgeDefinition edgeToRemove) {
		boolean returnValue = false;
		if(edgeToRemove != null && this.edges.contains(edgeToRemove)) {
			returnValue = this.edges.remove(edgeToRemove);
		}
		return returnValue;
	}
}

package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sobek.pgraph.NodeType;

@NamedQueries({
		@NamedQuery(name = NodeEntity.GET_CHILD_NODES_QUERY, query = "SELECT n FROM NodeEntity n, EdgeEntity e WHERE n.id = e.primaryKey.toNodeId and e.primaryKey.fromNodeId = :nodeId"),
		@NamedQuery(name = NodeEntity.GET_PARENT_NODES_QUERY, query = "SELECT n FROM NodeEntity n, EdgeEntity e WHERE n.id = e.primaryKey.fromNodeId and e.primaryKey.toNodeId = :nodeId") })
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "NODE")
public class NodeEntity {
	public static final String GET_CHILD_NODES_QUERY = "NodeEntity.getChildNode";
	public static final String GET_PARENT_NODES_QUERY = "NodeEntity.getParentNodes";

    @SequenceGenerator(name="NodeIdGenerator",
            sequenceName="NODE_ID_GENERATOR",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
         generator="NodeIdGenerator")
	@Column(name = "ID")
	@Id
	private long id;

	@Column(name = "PGRAPH_ID")
	private long pgraphId = -1L;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "NAME")
	private String name;

	// Required by JPA
	protected NodeEntity() {}

	protected NodeEntity(long pgraphId, String name, NodeType type) {
		this.pgraphId = pgraphId;
		this.type = type.toString();
		this.name = name;
	}

	public long getId() {
		return this.id;
	}

	public long getPgraphId() {
		return this.pgraphId;
	}

	public NodeType getType() {
		return NodeType.valueOf(type);
	}

	public String getName() {
		return this.name;
	}
}

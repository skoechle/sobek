package com.sobek.pgraph.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sobek.common.util.SystemProperties;

@Entity
@Table(name = "EDGE")
public class EdgeEntity {

	@EmbeddedId
	private EdgePrimaryKey primaryKey;

	@ManyToOne
	@JoinColumn(name = "PGRAPH_ID", insertable = false, updatable = false)
	PgraphEntity pgraph;

	@OneToOne()
	@JoinColumn(name = "FROM_NODE_ID", insertable = false, updatable = false)
	NodeEntity fromNode;

	@OneToOne()
	@JoinColumn(name = "TO_NODE_ID", insertable = false, updatable = false)
	NodeEntity toNode;

	@SuppressWarnings("unused")
	private EdgeEntity() {
		// Required by JPA
	}

	public EdgeEntity(PgraphEntity pgraph, NodeEntity fromNode,
			NodeEntity toNode) {
		if (pgraph == null || fromNode == null || toNode == null) {
			throw new IllegalArgumentException(
					"One or more invalid values were passed to the " + this.getClass().getName() +
					" constructor.  The given values " + "were:" + SystemProperties.NEW_LINE +
					"PGraph (cannot be null) : " + pgraph + SystemProperties.NEW_LINE +
					"From Node (cannot be null) : " + fromNode + SystemProperties.NEW_LINE +
					"To Node (cannot be null) : " + toNode + SystemProperties.NEW_LINE);
		}

		this.primaryKey = new EdgePrimaryKey(pgraph.getId(), fromNode.getId(), toNode.getId());
		this.pgraph = pgraph;
		this.pgraph.addEdge(this);
//		this.fromNode = fromNode;
//		this.toNode = toNode;
	}

	public long getPgraphId() {
		return primaryKey.getPgraphId();
	}

	public long getFromNodeId() {
		return primaryKey.getFromNodeId();
	}

	public long getToNodeId() {
		return primaryKey.getToNodeId();
	}
}

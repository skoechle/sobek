package com.sobek.pgraph;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;


@NamedQueries({
    @NamedQuery(name = "NodeEntity.getChildNodes",
	    	query = "SELECT n FROM NodeEntity n, EdgeEntity e WHERE e.fromNodeId = :nodeId and n.id = e.toNodeId"),
    @NamedQuery(name = "NodeEntity.getParentNodes",
	    	query = "SELECT n FROM NodeEntity n, EdgeEntity e WHERE e.toNodeId = :nodeId and n.id = e.fromNodeId")
})
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", discriminatorType=DiscriminatorType.STRING)
@Table(schema = "SOBEK", name = "NODE")
public class NodeEntity{
    public static final String GET_CHILD_NODES_QUERY = "NodeEntity.getChildNode";
    public static final String GET_PARENT_NODES_QUERY = "NodeEntity.getParentNodes";
    
    @Column(name = "ID")
    @GeneratedValue
    @Id
    private long id = -1L;
    
    @Column(name = "PGRAPH_ID")
    private long pgraphId = -1L;
    
    @Column(name = "TYPE")
    private String type;
    
    @Column(name = "JNDI_NAME")
    private String messageQueueName;
        
    @Transient
    private NodeType nodeType;
      
    protected NodeEntity(){
	// Required by JPA
    }
    
    public NodeEntity(long pgraphId, NodeType nodeType, String messageQueueName){
	this.pgraphId = pgraphId;
	this.type = nodeType.toString();
	this.nodeType = nodeType;
	this.messageQueueName = messageQueueName;
    }
    
    @PostLoad
    @SuppressWarnings("unused")
    private void setNodeType(){
	this.nodeType = NodeType.valueOf(this.type);
    }

    public long getId(){
        return this.id;
    }

    public long getPgraphId(){
        return this.pgraphId;
    }

    public NodeType getType(){
        return this.nodeType;
    }
    
    public String getMessageQueueName(){
	return this.messageQueueName;
    }

}

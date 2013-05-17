package com.sobek.pgraph.entity;

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

import com.sobek.pgraph.Node;
import com.sobek.pgraph.NodeType;

@NamedQueries({
    @NamedQuery(name = "NodeEntity.getChildNodes",
	    	query = "SELECT n FROM NodeEntity n, EdgeEntity e WHERE e.primaryKey.fromNodeId = :nodeId and n.id = e.primaryKey.toNodeId"),
    @NamedQuery(name = "NodeEntity.getParentNodes",
	    	query = "SELECT n FROM NodeEntity n, EdgeEntity e WHERE e.primaryKey.toNodeId = :nodeId and n.id = e.primaryKey.fromNodeId")
})
@Entity
<<<<<<< HEAD:source/sobek-workflow-engine/pgraph-ejb/src/main/java/com/sobek/pgraph/NodeEntity.java
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE", discriminatorType=DiscriminatorType.STRING)
@Table(schema = "SOBEK", name = "NODE")
=======
@Table(name = "NODE")
>>>>>>> 9647ec62ba437be570e3c4cd29adbed6bd31e471:source/sobek-workflow-engine/pgraph-ejb/src/main/java/com/sobek/pgraph/entity/NodeEntity.java
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
<<<<<<< HEAD:source/sobek-workflow-engine/pgraph-ejb/src/main/java/com/sobek/pgraph/NodeEntity.java
    
    public String getMessageQueueName(){
	return this.messageQueueName;
=======

    public Node getValue() throws NamingException{
	if(value == null){
	    value = (Node)InitialContext.doLookup(jndiName);
	}
	
	return value;
>>>>>>> 9647ec62ba437be570e3c4cd29adbed6bd31e471:source/sobek-workflow-engine/pgraph-ejb/src/main/java/com/sobek/pgraph/entity/NodeEntity.java
    }

}

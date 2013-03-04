package com.sobek.pgraph.entity;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sobek.pgraph.Node;
import com.sobek.pgraph.NodeType;

@NamedQueries({
    @NamedQuery(name = "NodeEntity.getChildNodes",
	    	query = "SELECT n FROM NodeEntity n, EdgeEntity e WHERE e.fromNodeId =: nodeId and n.id = e.toNodeId"),
    @NamedQuery(name = "NodeEntity.getParentNodes",
	    	query = "SELECT n FROM NodeEntity n, EdgeEntity e WHERE e.toNodeId =: nodeId and n.id = e.fromNodeId")
})
@Entity
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
    private String jndiName;
        
    @Transient
    private NodeType nodeType;
    
    @Transient 
    private Node value;
    
    private NodeEntity(){
	// Required by JPA
    }
    
    private NodeEntity(long pgraphId, NodeType nodeType, String jndiName){
	this.pgraphId = pgraphId;
	this.type = type.toString();
	this.nodeType = nodeType;
	this.jndiName = jndiName;
    }
    
    @PostLoad
    @SuppressWarnings("unused")
    private void setNodeType(){
	nodeType = NodeType.valueOf(type);
    }

    public long getId(){
        return id;
    }

    public long getPgraphId(){
        return pgraphId;
    }

    public NodeType getType(){
        return nodeType;
    }

    public Node getValue() throws NamingException{
	if(value == null){
	    value = (Node)InitialContext.doLookup(jndiName);
	}
	
	return value;
    }
}

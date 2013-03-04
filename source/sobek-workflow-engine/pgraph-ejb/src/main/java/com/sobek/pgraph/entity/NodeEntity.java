package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "SOBEK", name = "NODE")
public class NodeEntity{
    
    @Column(name = "ID")
    @GeneratedValue
    @Id
    private long id = -1L;
    
    @Column(name = "PGRAPH_ID")
    private long pgraphId = -1L;
    
    @Column(name = "NODE_TYPE_ID")
    private int nodeTypeId = -1;
    
    @Column(name = "JNDI_NAME")
    private String jndiName;
    
    private NodeEntity(){
	// Required by JPA
    }
    
    private NodeEntity(long pgraphId, int nodeTypeId, String jndiName){
	this.pgraphId = pgraphId;
	this.nodeTypeId = nodeTypeId;
	this.jndiName = jndiName;
    }

    public long getId(){
        return id;
    }

    public long getPgraphId(){
        return pgraphId;
    }

    public int getNodeTypeId(){
        return nodeTypeId;
    }

    public String getJndiName(){
        return jndiName;
    }    
}

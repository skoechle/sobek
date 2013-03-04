package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CurrentPositionPrimaryKey{
 
    @Column(name = "PGRAPH_ID")
    private long pgraphId = -1L;
    
    @Column(name = "NODE_ID")
    private long nodeId = -1L;
    
    @SuppressWarnings("unused")
    private CurrentPositionPrimaryKey(){
	// Required by JPA
    }
    
    public CurrentPositionPrimaryKey(long pgraphId, long nodeId){
	this.pgraphId = pgraphId;
	this.nodeId = nodeId;
    }

    public long getPgraphId(){
        return pgraphId;
    }
    
    public long getNodeId(){
        return nodeId;
    }
}

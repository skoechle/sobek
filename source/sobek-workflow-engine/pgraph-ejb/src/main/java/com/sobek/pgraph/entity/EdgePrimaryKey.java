package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EdgePrimaryKey{

    @Column(name = "PGRAPH_ID")
    private long pgraphId = -1L;
    
    @Column(name = "HEAD_NODE_ID")
    private long headNodeId = -1L;
    
    @Column(name = "TAIL_NODE_ID")
    private long tailNodeId = -1L;
    
    @SuppressWarnings("unused")
    private EdgePrimaryKey(){
	// Required by JPA
    }
    
    public EdgePrimaryKey(long pgraphId, long headNodeId, long tailNodeId){
	this.pgraphId = pgraphId;
	this.headNodeId = headNodeId;
	this.tailNodeId = tailNodeId;
    }

    public long getPgraphId(){
        return pgraphId;
    }

    public long getHeadNodeId(){
        return headNodeId;
    }

    public long getTailNodeId(){
        return tailNodeId;
    }
}

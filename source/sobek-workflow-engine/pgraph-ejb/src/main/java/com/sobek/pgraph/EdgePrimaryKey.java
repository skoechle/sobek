package com.sobek.pgraph;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EdgePrimaryKey{

    @Column(name = "PGRAPH_ID")
    private long pgraphId = -1L;
    
    @Column(name = "FROM_NODE_ID")
    private long fromNodeId = -1L;
    
    @Column(name = "TO_NODE_ID")
    private long toNodeId = -1L;
    
    @SuppressWarnings("unused")
    private EdgePrimaryKey(){
	// Required by JPA
    }
    
    public EdgePrimaryKey(long pgraphId, long fromNodeId, long toNodeId){
	this.pgraphId = pgraphId;
	this.fromNodeId = fromNodeId;
	this.toNodeId = toNodeId;
    }

    public long getPgraphId(){
        return pgraphId;
    }

    public long getFromNodeId(){
        return fromNodeId;
    }

    public long getToNodeId(){
        return toNodeId;
    }
}

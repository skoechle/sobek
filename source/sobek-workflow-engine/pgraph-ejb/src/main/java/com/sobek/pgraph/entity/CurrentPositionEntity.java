package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity()
@Table(schema = "SOBEK", name = "TRAVERSAL_QUEUE")
public class CurrentPositionEntity{
    
    @EmbeddedId
    private CurrentPositionPrimaryKey primaryKey;

    @SuppressWarnings("unused")
    private CurrentPositionEntity(){
	// Required by JPA
    }
    
    public CurrentPositionEntity(CurrentPositionPrimaryKey primaryKey){
	this.primaryKey = primaryKey;
    }

    public long getPgraphId(){
	return primaryKey.getPgraphId();
    }
    
    public long getNodeId(){
	return primaryKey.getNodeId();
    }
}

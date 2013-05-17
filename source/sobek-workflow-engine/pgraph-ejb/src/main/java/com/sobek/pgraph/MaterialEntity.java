package com.sobek.pgraph;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MaterialEntity extends NodeEntity{
    
    @Column(name = "STATE")
    private String state;
    
    protected MaterialEntity(){
	
    }
    
    public MaterialEntity(long pgraphId, NodeType type, String messageQueueName, MaterialState state){
	super(pgraphId, type, messageQueueName);
	this.state = state.toString();
    }
    
    public MaterialState getState(){
        return MaterialState.valueOf(this.state);
    }
    
    public void setState(MaterialState state){
	this.state = state.toString();
    }
}

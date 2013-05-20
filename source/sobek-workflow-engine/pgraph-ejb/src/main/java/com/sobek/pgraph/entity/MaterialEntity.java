package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.sobek.pgraph.MaterialState;
import com.sobek.pgraph.NodeType;

@Entity
public class MaterialEntity extends NodeEntity{
    
    @Column(name = "STATE")
    private String state;
    
    @Column(name = "value")
    @Lob
    private String value = null;
    
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
    
    public void setValue(String value){
	this.value = value;
    }
    
    public String getValue(){
	return this.value;
    }
}

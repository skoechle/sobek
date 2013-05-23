package com.sobek.pgraph.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.sobek.pgraph.MaterialState;
import com.sobek.pgraph.NodeType;

@Entity
@Table(name = "MATERIAL")
public class MaterialEntity extends NodeEntity{
    
    @Column(name = "STATE")
    private String state;
    
    @Column(name = "value")
    @Lob
    private String value = null;
    
    protected MaterialEntity(){
	
    }
    
    public MaterialEntity(long pgraphId, NodeType type, String name){
	super(pgraphId, type, name);
	this.state = MaterialState.NOT_AVAILABLE.toString();
    }
    
    public MaterialState getState(){
        return MaterialState.valueOf(this.state);
    }
    
    public void setState(MaterialState state){
	this.state = state.toString();
    }
    
    public void setValue(Serializable value){
	this.value = value.toString();
    }
    
    public Serializable getValue(){
	return this.value;
    }
}

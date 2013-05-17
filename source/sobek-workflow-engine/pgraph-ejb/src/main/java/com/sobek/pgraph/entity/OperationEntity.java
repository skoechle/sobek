package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sobek.pgraph.NodeType;
import com.sobek.pgraph.OperationState;


@Entity
@Table(name="OPERATION")
@DiscriminatorValue("OPERATION")
public class OperationEntity extends NodeEntity{
    
    @Column(name = "STATE")
    private String state;
    
    @SuppressWarnings("unused")
    private OperationEntity(){
	// Required by JPA.
    }
    
    public OperationEntity(long pgraphId, String messageQueueName, OperationState state){
	super(pgraphId, NodeType.OPERATION, messageQueueName);
	this.state = state.toString();
    }

    public OperationState getState(){
        return OperationState.valueOf(this.state);
    }
    
    public void setState(OperationState state){
	this.state = state.toString();
    }
}

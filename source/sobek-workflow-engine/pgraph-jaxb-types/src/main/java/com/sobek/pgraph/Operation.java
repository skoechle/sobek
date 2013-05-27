package com.sobek.pgraph;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "com.sobek.pgraph")
public class Operation extends Node{
    
    @XmlTransient
    private static final long serialVersionUID = 1L;
    
    @XmlElement(required = true)
    private OperationState state;

    @XmlElement(required = true)
	private String name;
    
    @SuppressWarnings("unused")
    private Operation(){
	// Required by JAXB.
    }
    
    public Operation(long id, String messageQueueName, OperationState state){
	super(id, messageQueueName);
	this.state = state;
    }
    
    public final NodeType getNodeType(){
	return NodeType.OPERATION;
    }
    
    public OperationState getState(){
	return state;
    }

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
}

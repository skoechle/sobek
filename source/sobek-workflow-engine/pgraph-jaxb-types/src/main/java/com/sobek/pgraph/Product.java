package com.sobek.pgraph;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "com.sobek.pgraph")
public class Product extends Node{
    
    @XmlTransient
    private static final long serialVersionUID = 1L;
    
    @SuppressWarnings("unused")
    private Product(){
	// Required by JAXB.
    }
    
    public Product(long id, String messageQueueName){
	super(id, messageQueueName);
    }
    
    public final NodeType getNodeType(){
	return NodeType.PRODUCT;
    }
}

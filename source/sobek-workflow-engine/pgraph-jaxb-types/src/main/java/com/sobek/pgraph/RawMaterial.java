package com.sobek.pgraph;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "com.sobek.pgraph")
public class RawMaterial extends Node{ 
    
    @XmlTransient
    private static final long serialVersionUID = 1L;
    
    @SuppressWarnings("unused")
    private RawMaterial(){
	// Rquired by JAXB.
    }
    
    public RawMaterial(long id, String messageQueueName){
	super(id, messageQueueName);
    }
    
    public final NodeType getNodeType(){
       return NodeType.RAW_MATERIAL;
   }
}

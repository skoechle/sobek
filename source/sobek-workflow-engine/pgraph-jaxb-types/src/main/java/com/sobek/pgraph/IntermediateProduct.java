package com.sobek.pgraph;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "com.sobek.pgraph")
public class IntermediateProduct extends Node{
    
    @XmlTransient
    private static final long serialVersionUID = 1L;
    
    @SuppressWarnings("unused")
    private IntermediateProduct(){
	// Required by JAXB
    }

    public IntermediateProduct(long id, String messageQUeueName){
	super(id, messageQUeueName);
    }
    
    public final NodeType getNodeType(){
	return NodeType.INTERMEDIATE_PRODUCT;
    }
}

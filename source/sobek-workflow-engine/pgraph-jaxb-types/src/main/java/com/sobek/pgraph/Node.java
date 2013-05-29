package com.sobek.pgraph;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "com.sobek.pgraph")
public abstract class Node implements Serializable{
    
    @XmlTransient
    private static final long serialVersionUID = 1L;
    
    @XmlElement(required = true)
    private long id = -1L;
    
    @XmlElement(required = true)
    private String name;

    public abstract NodeType getNodeType();
    
    
    protected Node(){
	// Required by JAXB
    }
    
    public Node(long id, String name){
	if(id < 0 || name == null || name.isEmpty()){
	    String message = "An invalid parameter was passed. " + 
		    "Id cannot be negative and messageQueueName cannot be null or empty. " + 
		    "Parameters were:\n" + 
		    "id: " + id + "\n" +
		    "messageQueueName: '" + name + "'";
	    			
	    throw new IllegalArgumentException(message);
	}
	
	this.id = id;
	this.name = name;
    }

    /*
     * Package protected since only the pgraph manager (and JAXB) should be allowed
     * to set this.
     */
    void setId(long id){
	this.id = id;
    }

    public final long getId(){
	return this.id;
    }
    
    public String getName(){
	return name;
    }
}
package com.sobek.pgraph;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "com.sobek.pgraph")
public final class Edge implements Serializable{
    
    @XmlTransient
    private static final long serialVersionUID = 1L;
    
    @XmlElement(required = true)
    private long fromNodeId;
    
    @XmlElement(required = true)
    private long toNodeId;
    
    @SuppressWarnings("unused")
    private Edge(){
	// Required by JAXB
    }
    
    public Edge(long fromNodeId, long toNodeId){
	if(fromNodeId < 0 || toNodeId < 0){
	    String message = "Paramteres can't be negative. Paramteres were:\n" +
		    	"fromNodeId: " + fromNodeId + "\n" +
		    	"toNodeId: " + toNodeId + "\n";
	    
	    throw new IllegalArgumentException(message);
	}
	
	this.fromNodeId = fromNodeId;
	this.toNodeId = toNodeId;
    }

    public long getFromNode(){
        return fromNodeId;
    }

    public long getToNode(){
        return toNodeId;
    }
}

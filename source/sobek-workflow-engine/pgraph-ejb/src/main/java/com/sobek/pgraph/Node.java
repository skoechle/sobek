package com.sobek.pgraph;

import java.io.Serializable;

public abstract class Node implements Serializable{
    private static final long serialVersionUID = 1L;

    private long id = -1L;

    public abstract String getJndiName();
    public abstract NodeType getNodeType();
    public abstract void persist();

    /*
     * Package protected since only the pgraph manager should be allowed
     * to set this.
     */
    void setId(long id){
	this.id = id;
    }

    public final long getId(){
	return this.id;
    }
}
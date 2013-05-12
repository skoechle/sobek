package com.sobek.pgraph.material;

import java.io.Serializable;

import com.sobek.pgraph.Node;


public abstract class Material extends Node{
    private static final long serialVersionUID = 1L;
    
    public abstract Serializable getValue();
    public abstract boolean isAvailable();
}

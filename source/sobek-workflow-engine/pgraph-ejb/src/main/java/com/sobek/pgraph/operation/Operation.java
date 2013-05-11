package com.sobek.pgraph.operation;

import com.sobek.pgraph.Node;
import com.sobek.pgraph.NodeType;

public abstract class Operation extends Node{
    private static final long serialVersionUID = 1L;

    public abstract OperationState getState();
    
    public final NodeType getNodeType(){
	return NodeType.OPERATION;
    }
}

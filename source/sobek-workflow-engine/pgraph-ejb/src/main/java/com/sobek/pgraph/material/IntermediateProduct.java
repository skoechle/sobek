package com.sobek.pgraph.material;

import com.sobek.pgraph.NodeType;


public abstract class IntermediateProduct extends Material{
    private static final long serialVersionUID = 1L;

    public final NodeType getNodeType(){
	return NodeType.INTERMEDIATE_PRODUCT;
    }
}

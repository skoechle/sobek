package com.sobek.pgraph.material;

import com.sobek.pgraph.Node;

public interface Material extends Node{
    public Object getValue();
    public MaterialType getMaterialType();
}

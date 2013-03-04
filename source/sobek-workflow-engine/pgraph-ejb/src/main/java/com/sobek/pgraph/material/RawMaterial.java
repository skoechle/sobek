package com.sobek.pgraph.material;

public abstract class RawMaterial implements Material{
    
    public final MaterialType getMaterialType(){
	return MaterialType.RAW;
    }
}

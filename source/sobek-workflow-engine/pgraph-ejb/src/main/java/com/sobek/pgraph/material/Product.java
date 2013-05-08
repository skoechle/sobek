package com.sobek.pgraph.material;

public abstract class Product implements Material{
    
    public final MaterialType getMaterialType(){
	return MaterialType.PRODUCT;
    }
    
}

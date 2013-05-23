package com.sobek.pgraph.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sobek.pgraph.NodeType;

@Entity
@Table(name="MATERIAL")
@DiscriminatorValue("INTERMEDIATE_PRODUCT")
public class RawMaterialEntity extends MaterialEntity{
  
    @SuppressWarnings("unused")
    private RawMaterialEntity(){
	// Required by JPA
    }
    
    public RawMaterialEntity(long pgraphId, String name){
	super(pgraphId, NodeType.RAW_MATERIAL, name);
    }
}


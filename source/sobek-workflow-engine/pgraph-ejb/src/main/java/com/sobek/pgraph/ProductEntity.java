package com.sobek.pgraph;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="MATERIAL")
@DiscriminatorValue("PRODUCT")
public class ProductEntity extends MaterialEntity{
    
    @SuppressWarnings("unused")
    private ProductEntity(){
	// Required by JPA
    }
    
    public ProductEntity(long pgraphId, String messageQueueName, MaterialState state){
	super(pgraphId, NodeType.PRODUCT, messageQueueName, state);
    }
}

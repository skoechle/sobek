package com.sobek.pgraph;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="MATERIAL")
@DiscriminatorValue("INTERMEDIATE_PRODUCT")
public class IntermediateProductEntity extends MaterialEntity{
    
    @SuppressWarnings("unused")
    private IntermediateProductEntity(){
	// Required by JPA
    }
    
    public IntermediateProductEntity(long pgraphId, String messageQueueName, MaterialState state){
	super(pgraphId, NodeType.INTERMEDIATE_PRODUCT, messageQueueName, state);
    }
}

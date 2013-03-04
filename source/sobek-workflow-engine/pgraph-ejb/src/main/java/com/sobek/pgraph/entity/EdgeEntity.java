package com.sobek.pgraph.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "SOBEK", name = "EDGE")
public class EdgeEntity{
    
   @EmbeddedId
   private EdgePrimaryKey primaryKey;
    
   @SuppressWarnings("unused")
   private EdgeEntity(){
       // Required by JPA
   }
   
   public EdgeEntity(EdgePrimaryKey primaryKey){
       this.primaryKey = primaryKey;
   }
   
   public long getPgraphId(){
       return primaryKey.getPgraphId();
   }

   public long getHeadNodeId(){
       return primaryKey.getHeadNodeId();
   }

   public long getTailNodeId(){
       return primaryKey.getTailNodeId();
   }
}

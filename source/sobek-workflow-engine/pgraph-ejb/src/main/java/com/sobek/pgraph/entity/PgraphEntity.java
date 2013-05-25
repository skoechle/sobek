package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "PgraphEntity.countByPgraphId",
  	        query = "SELECT count(p.id) FROM PgraphEntity p WHERE p.id = :pgraphId"),
    @NamedQuery(name = "PgraphEntity.getRawMaterial",
	    	query = "SELECT n FROM NodeEntity n WHERE n.pgraphId = :pgraphId and n.type = 'RAW_MATERIAL'")
})
@Entity
@Table(name = "PGRAPH")
public class PgraphEntity{

    public static final String COUNT_BY_ID_QUERY = "PgraphEntity.countByPgraphId";
    public static final String GET_RAW_MATERIAL_QUERY = "PgraphEntity.getRawMaterial";
    
    @SequenceGenerator(name="PgraphIdGenerator",
            sequenceName="PGRAPH_ID_GENERATOR",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
         generator="PgraphIdGenerator")
    @Id
    @Column(name = "ID")
    private long id;
    
    public long getId(){
	return id;
    }
}

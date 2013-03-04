package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "PgraphEntity.countByPgraphId",
  	        query = "SELECT count(PgraphEntity.id) FROM PgraphEntity p WHERE p.id =: pgraphId"),
    @NamedQuery(name = "PgraphEntity.getRawMaterial",
	    	query = "SELECT * FROM NodeEntity n WHERE n.pgraphId =: pgraphId and n.type = 'RAW_MATERIAL'")
})
@Entity
@Table(schema = "SOBEK", name = "PGRAPH")
public class PgraphEntity{

    public static final String COUNT_BY_ID_QUERY = "PgraphEntity.countByPgraphId";
    public static final String GET_RAW_MATERIAL_QUERY = "PgraphEntity.getRawMaterial";
    
    @GeneratedValue
    @Id
    @Column(name = "ID")
    private long id = -1L;
    
    public long getId(){
	return id;
    }
}

package com.sobek.pgraph.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "PgraphEntity.countByPgraphId",
  	        query = "SELECT count(p.id) FROM PgraphEntity p WHERE p.id = :pgraphId")
})
@Entity
@Table(name = "PGRAPH")
public class PgraphEntity{

    public static final String COUNT_BY_ID_QUERY = "PgraphEntity.countByPgraphId";
    
    @SequenceGenerator(name="PgraphIdGenerator",
            sequenceName="PGRAPH_ID_GENERATOR",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
         generator="PgraphIdGenerator")
    @Id
    @Column(name = "ID")
    private long id;
    
    
    @OneToOne(fetch=FetchType.EAGER, optional=true)
    @JoinColumn(name = "RAW_MATERIAL_ID", insertable=false, updatable=false)
    RawMaterialEntity rawMaterial;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="pgraph", cascade=CascadeType.ALL)
    List<EdgeEntity> edges = new ArrayList<EdgeEntity>();
    
    public long getId() {
    	return id;
    }

	public void setRawMaterial(RawMaterialEntity rawMaterial) {
		this.rawMaterial = rawMaterial;
	}
    
    public RawMaterialEntity getRawMaterial() {
    	return this.rawMaterial;
    }
    
    public List<EdgeEntity> getEdges() {
    	return this.edges;
    }
    
    public void addEdge(EdgeEntity edge) {
    	this.edges.add(edge);
    }
}

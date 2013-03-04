package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "SOBEK", name = "PGRAPH")
public class PgraphEntity{

    @GeneratedValue
    @Id
    @Column(name = "ID")
    private long id = -1L;
    
    public long getId(){
	return id;
    }
}

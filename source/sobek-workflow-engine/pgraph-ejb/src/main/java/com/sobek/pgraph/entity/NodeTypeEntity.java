package com.sobek.pgraph.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NODE_TYPE")
public class NodeTypeEntity{

    @Column(name = "ID")
    @Id
    private int id = -1;
    
    @Column(name = "name")
    private String name;

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}

package com.sobek.pgraph;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PgraphDao{
    
    @PersistenceContext(unitName="sobek-pgraph")
    private EntityManager entityManager;
    
    
    
    
    
}

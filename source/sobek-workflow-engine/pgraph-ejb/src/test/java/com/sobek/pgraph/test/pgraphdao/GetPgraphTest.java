package com.sobek.pgraph.test.pgraphdao;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.sobek.pgraph.PgraphDao;
import com.sobek.pgraph.entity.PgraphEntity;

public class GetPgraphTest{
    
    @Test
    public void validParam() throws Exception{
	// Setup
	EntityManager entityManager = Mockito.mock(EntityManager.class);
	
	long pgraphId = 5;
	PgraphEntity pgraphEntity = new PgraphEntity();
	
	Mockito.when(entityManager.find(PgraphEntity.class, pgraphId)).thenReturn(pgraphEntity);
	
	// Call the dao
	PgraphDao dao = createPgraphDao(entityManager);
	PgraphEntity result = dao.getPgraph(pgraphId);
	
	// Check result
	Assert.assertEquals(pgraphEntity, result);
    }
    
    @Test
    public void noGraphFound() throws Exception{	
	// Setup
	EntityManager entityManager = Mockito.mock(EntityManager.class);
	
	long pgraphId = 5;
	
	Mockito.when(entityManager.find(PgraphEntity.class, pgraphId)).thenReturn(null);
	
	// Call the dao
	PgraphDao dao = createPgraphDao(entityManager);
	PgraphEntity result = dao.getPgraph(pgraphId);
	
	// Result should be null
	Assert.assertNull(result);
    }
    
    private PgraphDao createPgraphDao(EntityManager em) throws Exception{
	PgraphDao dao = new PgraphDao();
	
	Field emField = dao.getClass().getField("entityManager");
	emField.setAccessible(true);
	emField.set(dao, em);
	
	return dao;
    }
}

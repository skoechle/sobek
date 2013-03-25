package com.sobek.pgraph.test.pgraphdao;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.sobek.pgraph.PgraphDao;
import com.sobek.pgraph.entity.PgraphEntity;

public class PgraphExistsTest{
    
    @SuppressWarnings("unchecked")
    @Test
    public void graphExistsTest() throws Exception{
	// Setup
	TypedQuery<Integer> query = Mockito.mock(TypedQuery.class);
	Mockito.when(query.getSingleResult()).thenReturn(1);
	
	EntityManager em = Mockito.mock(EntityManager.class);
	Mockito.when(em.createNamedQuery(PgraphEntity.COUNT_BY_ID_QUERY, Integer.class)).thenReturn(query);
	
	PgraphDao dao = createPgraphDao(em);
	
	// Dao should return true.
	Assert.assertTrue(dao.pgraphExists(1));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void graphDoesNotExistTest() throws Exception{
	// Setup
	TypedQuery<Integer> query = Mockito.mock(TypedQuery.class);
	Mockito.when(query.getSingleResult()).thenReturn(0);
	
	EntityManager em = Mockito.mock(EntityManager.class);
	Mockito.when(em.createNamedQuery(PgraphEntity.COUNT_BY_ID_QUERY, Integer.class)).thenReturn(query);
	
	PgraphDao dao = createPgraphDao(em);
	
	// Dao should return false.
	Assert.assertFalse(dao.pgraphExists(1));
    }
    
    private PgraphDao createPgraphDao(EntityManager em) throws Exception{
   	PgraphDao dao = new PgraphDao();
   	
   	Field emField = PgraphDao.class.getDeclaredField("entityManager");
   	emField.setAccessible(true);
   	emField.set(dao, em);
   	
   	return dao;
    }
}

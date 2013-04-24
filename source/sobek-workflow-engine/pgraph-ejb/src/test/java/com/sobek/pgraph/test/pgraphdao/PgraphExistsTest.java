package com.sobek.pgraph.test.pgraphdao;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.sobek.pgraph.PgraphDaoBean;
import com.sobek.pgraph.PgraphDaoLocal;
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
	
	PgraphDaoLocal dao = createPgraphDao(em);
	
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
	
	PgraphDaoLocal dao = createPgraphDao(em);
	
	// Dao should return false.
	Assert.assertFalse(dao.pgraphExists(1));
    }
    
    private PgraphDaoLocal createPgraphDao(EntityManager em) throws Exception{
	PgraphDaoLocal dao = new PgraphDaoBean();
   	
   	Field emField = PgraphDaoBean.class.getDeclaredField("entityManager");
   	emField.setAccessible(true);
   	emField.set(dao, em);
   	
   	return dao;
    }
}

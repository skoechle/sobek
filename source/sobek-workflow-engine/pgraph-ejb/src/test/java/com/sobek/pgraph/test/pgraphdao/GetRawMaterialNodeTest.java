package com.sobek.pgraph.test.pgraphdao;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.sobek.pgraph.NodeType;
import com.sobek.pgraph.PgraphDao;
import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.entity.PgraphEntity;
import com.sobek.pgraph.exception.InvalidPgraphStructureException;
import com.sobek.pgraph.exception.NoSuchPgraphException;

public class GetRawMaterialNodeTest{
    
    @SuppressWarnings("unchecked")
    @Test
    public void validParamTest() throws Exception{
	// Setup
	long pgraphId = 1L;
	NodeEntity node = new NodeEntity(pgraphId, NodeType.RAW_MATERIAL, "jndiName");
	
	TypedQuery<NodeEntity> query = Mockito.mock(TypedQuery.class);
	Mockito.when(query.getSingleResult()).thenReturn(node);
	
	EntityManager em = Mockito.mock(EntityManager.class);
	Mockito.when(em.createNamedQuery(PgraphEntity.GET_RAW_MATERIAL_QUERY, NodeEntity.class)).thenReturn(query);
	
	PgraphDao dao = createPgraphDao(em);
	
	// Call the DAO
	NodeEntity result = dao.getRawMaterialNode(pgraphId);
	
	// Check result
	Assert.assertEquals(node, result);
    }
    
    
    @SuppressWarnings("unchecked")
    @Test(expected = NoSuchPgraphException.class)
    public void noSuchPgraphTest() throws Exception{
	// Setup
	long pgraphId = 1L;

        TypedQuery<NodeEntity> query = Mockito.mock(TypedQuery.class);
	Mockito.when(query.getSingleResult()).thenThrow(NoResultException.class);
	
	TypedQuery<Integer> existsQuery = Mockito.mock(TypedQuery.class);
	Mockito.when(existsQuery.getSingleResult()).thenReturn(0);
	
	EntityManager em = Mockito.mock(EntityManager.class);
	Mockito.when(em.createNamedQuery(PgraphEntity.GET_RAW_MATERIAL_QUERY, NodeEntity.class)).thenReturn(query);
	Mockito.when(em.createNamedQuery(PgraphEntity.COUNT_BY_ID_QUERY, Integer.class)).thenReturn(existsQuery);
	
	PgraphDao dao = createPgraphDao(em);

	// Call the DAO. It should throw a NoSuchPgraphException
	dao.getRawMaterialNode(pgraphId);
    }
    
    @SuppressWarnings("unchecked")
    @Test(expected = InvalidPgraphStructureException.class)
    public void noRawMaterialNode() throws Exception{
	// Setup
	long pgraphId = 1L;

        TypedQuery<NodeEntity> query = Mockito.mock(TypedQuery.class);
	Mockito.when(query.getSingleResult()).thenThrow(NoResultException.class);
	
	TypedQuery<Integer> existsQuery = Mockito.mock(TypedQuery.class);
	Mockito.when(existsQuery.getSingleResult()).thenReturn(1);
	
	EntityManager em = Mockito.mock(EntityManager.class);
	Mockito.when(em.createNamedQuery(PgraphEntity.GET_RAW_MATERIAL_QUERY, NodeEntity.class)).thenReturn(query);
	Mockito.when(em.createNamedQuery(PgraphEntity.COUNT_BY_ID_QUERY, Integer.class)).thenReturn(existsQuery);
	
	PgraphDao dao = createPgraphDao(em);

	// Call the DAO. It should throw an InvalidPgraphStructureException.
	dao.getRawMaterialNode(pgraphId);
    }
    
    @SuppressWarnings("unchecked")
    @Test(expected = InvalidPgraphStructureException.class)
    public void multipleRawMaterialNodes() throws Exception{
	// Setup
	long pgraphId = 1L;

        TypedQuery<NodeEntity> query = Mockito.mock(TypedQuery.class);
	Mockito.when(query.getSingleResult()).thenThrow(NonUniqueResultException.class);
		
	EntityManager em = Mockito.mock(EntityManager.class);
	Mockito.when(em.createNamedQuery(PgraphEntity.GET_RAW_MATERIAL_QUERY, NodeEntity.class)).thenReturn(query);
	
	PgraphDao dao = createPgraphDao(em);

	// Call the DAO. It should throw an InvalidPgraphStructureException.
	dao.getRawMaterialNode(pgraphId);
    }
    
    
    private PgraphDao createPgraphDao(EntityManager em) throws Exception{
   	PgraphDao dao = new PgraphDao();
   	
   	Field emField = dao.getClass().getField("EntityManager");
   	emField.setAccessible(true);
   	emField.set(dao, em);
   	
   	return dao;
    }
}

package com.sobek.pgraph.test.pgraphdao;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.sobek.pgraph.NodeType;
import com.sobek.pgraph.PgraphDao;
import com.sobek.pgraph.entity.NodeEntity;

public class GetParentNodesTest{
    
    @SuppressWarnings("unchecked")
    @Test
    public void validParameterTest() throws Exception{
	// Setup
	EntityManager em = Mockito.mock(EntityManager.class);
        TypedQuery<NodeEntity> query = (TypedQuery<NodeEntity>)Mockito.mock(TypedQuery.class);
        
        List<NodeEntity> resultList = new LinkedList<NodeEntity>();
        resultList.add(new NodeEntity(1L, NodeType.INTERMEDIATE_PRODUCT, "jndiname"));
        
        Mockito.when(query.getResultList()).thenReturn(resultList);
	Mockito.when(em.createNamedQuery(NodeEntity.GET_PARENT_NODES_QUERY, NodeEntity.class)).thenReturn(query);
	
	PgraphDao dao = createPgraphDao(em);
	
	// Call the dao
	List<NodeEntity> parentNodes = dao.getParentNodes(1L);
	
	// Check the list
	Assert.assertEquals(resultList, parentNodes);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void noParentNodesTest() throws Exception{
	// Setup
	EntityManager em = Mockito.mock(EntityManager.class);
        TypedQuery<NodeEntity> query = (TypedQuery<NodeEntity>)Mockito.mock(TypedQuery.class);
        
        List<NodeEntity> resultList = new LinkedList<NodeEntity>();
        
        Mockito.when(query.getResultList()).thenReturn(resultList);
	Mockito.when(em.createNamedQuery(NodeEntity.GET_PARENT_NODES_QUERY, NodeEntity.class)).thenReturn(query);
	
	PgraphDao dao = createPgraphDao(em);
	
	// Call the dao
	List<NodeEntity> parentNodes = dao.getParentNodes(1L);
	
	// Check the list. It should be empty
	Assert.assertTrue(parentNodes.isEmpty());
    }
    
    private PgraphDao createPgraphDao(EntityManager em) throws Exception{
	PgraphDao dao = new PgraphDao();
	
	Field emField = PgraphDao.class.getDeclaredField("entityManager");
	emField.setAccessible(true);
	emField.set(dao, em);
	
	return dao;
    }
}

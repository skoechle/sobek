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
import com.sobek.pgraph.PgraphDaoBean;
import com.sobek.pgraph.PgraphDaoLocal;
import com.sobek.pgraph.entity.NodeEntity;

public class GetChildNodesTest{
    
    @SuppressWarnings("unchecked")
    @Test
    public void validParameterTest() throws Exception{
	// Setup
	EntityManager em = Mockito.mock(EntityManager.class);
        TypedQuery<NodeEntity> query = (TypedQuery<NodeEntity>)Mockito.mock(TypedQuery.class);
        
        List<NodeEntity> resultList = new LinkedList<NodeEntity>();
        resultList.add(new NodeEntity(1L, NodeType.INTERMEDIATE_PRODUCT, "jndiname"));
        
        Mockito.when(query.getResultList()).thenReturn(resultList);
	Mockito.when(em.createNamedQuery(NodeEntity.GET_CHILD_NODES_QUERY, NodeEntity.class)).thenReturn(query);
	
	PgraphDaoLocal dao = createPgraphDao(em);
	
	// Call the dao
	List<NodeEntity> parentNodes = dao.getChildNodes(1L);
	
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
	Mockito.when(em.createNamedQuery(NodeEntity.GET_CHILD_NODES_QUERY, NodeEntity.class)).thenReturn(query);
	
	PgraphDaoLocal dao = createPgraphDao(em);
	
	// Call the dao
	List<NodeEntity> parentNodes = dao.getChildNodes(1L);
	
	// Check the list. It should be empty
	Assert.assertTrue(parentNodes.isEmpty());
    }
    
    private PgraphDaoLocal createPgraphDao(EntityManager em) throws Exception{
	PgraphDaoLocal dao = new PgraphDaoBean();
	
	Field emField = PgraphDaoBean.class.getDeclaredField("entityManager");
	emField.setAccessible(true);
	emField.set(dao, em);
	
	return dao;
    }
}

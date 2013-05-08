package com.sobek.pgraph.test.pgraphmanagerbean;

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sobek.pgraph.NoSuchPgraphException;
import com.sobek.pgraph.NodeType;
import com.sobek.pgraph.PgraphDaoBean;
import com.sobek.pgraph.PgraphDaoLocal;
import com.sobek.pgraph.PgraphManagerBean;
import com.sobek.pgraph.entity.EdgeEntity;
import com.sobek.pgraph.entity.EdgePrimaryKey;
import com.sobek.pgraph.entity.NodeEntity;
import com.sobek.pgraph.material.Product;
import com.sobek.pgraph.material.RawMaterial;
import com.sobek.pgraph.operation.Operation;
import com.sobek.pgraph.operation.OperationState;

public class GetReadyOperationsTest{
    private static Logger logger = LoggerFactory.getLogger(GetReadyOperationsTest.class);
    
    @Test(expected = NoSuchPgraphException.class)
    public void pgraphIdDoesNotExist() throws Exception{
	logger.info("\n\n=================pgraphIdDoesNotExist=================\n");
	// Setup4
	long pgraphId = 3L;
	
	PgraphDaoBean dao = Mockito.mock(PgraphDaoBean.class);
	Mockito.when(dao.pgraphExists(pgraphId)).thenReturn(false);

	PgraphManagerBean bean = createPgraphManagerBean(dao);
	
	// Expect an exception to be thrown.
	bean.getReadyOperations(pgraphId);
    }
    
    @Test(timeout = (10000))
    public void oneOperationRawAvailableTest() throws Exception{
	logger.info("\n\n=================oneOperationRawAvailableTest=================\n");
	
	// Setup
	long pgraphId = 1L;
	MockDao pgraphDao = new MockDao(pgraphId);
	
	NodeEntity rootNode = new NodeEntity(pgraphId, NodeType.RAW_MATERIAL, "JNDI_NAME");
	RawMaterial rootNodeValue = Mockito.mock(RawMaterial.class);
	Mockito.when(rootNodeValue.isAvailable()).thenReturn(true);
	
	NodeEntity operationNode = new NodeEntity(pgraphId, NodeType.OPERATION, "JNDI_NAME");
	Operation operationNodeValue = Mockito.mock(Operation.class);
	Mockito.when(operationNodeValue.getState()).thenReturn(OperationState.NOT_STARTED);
	
	NodeEntity productNode = new NodeEntity(pgraphId, NodeType.PRODUCT, "JNDI_NAME");
	Product productNodeValue = Mockito.mock(Product.class);
	
	long rootNodeId = pgraphDao.addNode(rootNode, rootNodeValue);
	long operationNodeId = pgraphDao.addNode(operationNode, operationNodeValue);
	long productNodeId = pgraphDao.addNode(productNode, productNodeValue);
	
	EdgeEntity edge1 = new EdgeEntity(new EdgePrimaryKey(pgraphId, rootNodeId, operationNodeId));
	EdgeEntity edge2 = new EdgeEntity(new EdgePrimaryKey(pgraphId, operationNodeId, productNodeId));
	
	pgraphDao.addEdge(edge1);
	pgraphDao.addEdge(edge2);
	
	PgraphManagerBean bean = createPgraphManagerBean(pgraphDao);
	
	// Call the bean
	List<Operation> readyOps = bean.getReadyOperations(pgraphId);
	
	// Verify that the operation is returned.
	
	Assert.assertEquals(1, readyOps.size());
	Assert.assertEquals(operationNodeValue, readyOps.get(0));
    }
      
    private PgraphManagerBean createPgraphManagerBean(PgraphDaoLocal pgraphDao) throws Exception{
	PgraphManagerBean bean = new PgraphManagerBean();
	
	Field daoField = bean.getClass().getDeclaredField("pgraphDao");
	daoField.setAccessible(true);
	daoField.set(bean, pgraphDao);
	
	return bean;
    }
    
}

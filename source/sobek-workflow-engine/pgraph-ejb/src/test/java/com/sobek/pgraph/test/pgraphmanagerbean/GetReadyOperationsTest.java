package com.sobek.pgraph.test.pgraphmanagerbean;

import java.lang.reflect.Field;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
import com.sobek.pgraph.EdgeEntity;
import com.sobek.pgraph.EdgePrimaryKey;
import com.sobek.pgraph.MaterialState;
import com.sobek.pgraph.NoSuchPgraphException;
import com.sobek.pgraph.Operation;
import com.sobek.pgraph.OperationEntity;
import com.sobek.pgraph.OperationState;
import com.sobek.pgraph.PgraphDaoBean;
import com.sobek.pgraph.PgraphDaoLocal;
import com.sobek.pgraph.PgraphManagerBean;
import com.sobek.pgraph.ProductEntity;
import com.sobek.pgraph.RawMaterialEntity;
=======
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
>>>>>>> 9647ec62ba437be570e3c4cd29adbed6bd31e471

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
	
	RawMaterialEntity rootNode = new RawMaterialEntity(pgraphId, "queueName", MaterialState.AVAILABLE);
	OperationEntity operationNode = new OperationEntity(pgraphId, "OperQueueName", OperationState.NOT_STARTED);
	ProductEntity productNode = new ProductEntity(pgraphId, "queueName", MaterialState.NOT_AVAILABLE);
	
	pgraphDao.addNode(rootNode);
	long rootNodeId = rootNode.getId();
	
	pgraphDao.addNode(operationNode);
	long operationNodeId = operationNode.getId();
	
	pgraphDao.addNode(productNode);
	long productNodeId = productNode.getId();
	
	Operation expectedResult = new Operation(operationNodeId, operationNode.getMessageQueueName(), operationNode.getState());
	
	EdgeEntity edge1 = new EdgeEntity(new EdgePrimaryKey(pgraphId, rootNodeId, operationNodeId));
	EdgeEntity edge2 = new EdgeEntity(new EdgePrimaryKey(pgraphId, operationNodeId, productNodeId));
	
	pgraphDao.addEdge(edge1);
	pgraphDao.addEdge(edge2);
	
	PgraphManagerBean bean = createPgraphManagerBean(pgraphDao);
	
	// Call the bean
	List<Operation> readyOps = bean.getReadyOperations(pgraphId);
	
	// Verify that the operation is returned.
	Assert.assertEquals(1, readyOps.size());
	
	Operation result = readyOps.get(0);
	Assert.assertEquals(expectedResult.getId(), result.getId());
	Assert.assertEquals(expectedResult.getMessageQueueName(), result.getMessageQueueName());
	Assert.assertEquals(expectedResult.getNodeType(), result.getNodeType());
	Assert.assertEquals(expectedResult.getState(), result.getState());
    }
      
    private PgraphManagerBean createPgraphManagerBean(PgraphDaoLocal pgraphDao) throws Exception{
	PgraphManagerBean bean = new PgraphManagerBean();
	
	Field daoField = bean.getClass().getDeclaredField("pgraphDao");
	daoField.setAccessible(true);
	daoField.set(bean, pgraphDao);
	
	return bean;
    }
    
}

package com.sobek.pgraph.test.pgraphmanagerbean;

import org.junit.Test;

public class GetReadyOperationsTest {
//	private static Logger logger = LoggerFactory
//			.getLogger(GetReadyOperationsTest.class);

//	@Test(expected = NoSuchPgraphException.class)
//	public void pgraphIdDoesNotExist() throws Exception {
//		logger.info("\n\n=================pgraphIdDoesNotExist=================\n");
//		// Setup4
//		long pgraphId = 3L;
//
//		PgraphDaoBean dao = Mockito.mock(PgraphDaoBean.class);
//		Mockito.when(dao.pgraphExists(pgraphId)).thenReturn(false);
//
//		PgraphManagerBean bean = createPgraphManagerBean(dao);
//
//		// Expect an exception to be thrown.
//		bean.getReadyOperations(pgraphId);
//	}

	@Test(timeout = (10000))
	public void oneOperationRawAvailableTest() throws Exception {
//		logger.info("\n\n=================oneOperationRawAvailableTest=================\n");
//
//		// Setup
//		long pgraphId = 1L;
//		MockDao pgraphDao = new MockDao(pgraphId);
//
//		RawMaterialEntity rootNode = new RawMaterialEntity(pgraphId, "nodeName");
//		rootNode.setState(MaterialState.AVAILABLE);
//
//		OperationEntity operationNode = new OperationEntity(pgraphId,
//				"OperQueueName", "nodeName");
//		ProductEntity productNode = new ProductEntity(pgraphId, "nodeName");
//
//		Field inputMaterailsField = OperationEntity.class
//				.getDeclaredField("inputMaterials");
//		inputMaterailsField.setAccessible(true);
//		HashSet<MaterialEntity> inputMaterials = new HashSet<MaterialEntity>();
//		inputMaterials.add(rootNode);
//		inputMaterailsField.set(operationNode, inputMaterials);
//
//		Field outputMaterialsField = OperationEntity.class
//				.getDeclaredField("outputMaterials");
//		outputMaterialsField.setAccessible(true);
//		HashSet<MaterialEntity> outputMaterials = new HashSet<MaterialEntity>();
//		outputMaterials.add(productNode);
//		outputMaterialsField.set(operationNode, outputMaterials);
//
//		pgraphDao.addNode(rootNode);
//		long rootNodeId = rootNode.getId();
//
//		pgraphDao.addNode(operationNode);
//		long operationNodeId = operationNode.getId();
//
//		pgraphDao.addNode(productNode);
//		long productNodeId = productNode.getId();
//
//		Operation expectedResult = new Operation(operationNodeId,
//				operationNode.getName(), operationNode.getMessageQueueName());
//
//		EdgeEntity edge1 = new EdgeEntity(new EdgePrimaryKey(pgraphId,
//				rootNodeId, operationNodeId));
//		EdgeEntity edge2 = new EdgeEntity(new EdgePrimaryKey(pgraphId,
//				operationNodeId, productNodeId));
//
//		pgraphDao.addEdge(edge1);
//		pgraphDao.addEdge(edge2);
//
//		PgraphManagerBean bean = createPgraphManagerBean(pgraphDao);
//
//		// Call the bean
//		List<Operation> readyOps = bean.getReadyOperations(pgraphId);
//
//		// Verify that the operation is returned.
//		Assert.assertEquals(1, readyOps.size());
//
//		Operation result = readyOps.get(0);
//		Assert.assertEquals(expectedResult.getId(), result.getId());
//		Assert.assertEquals(expectedResult.getName(), result.getName());
//		Assert.assertEquals(expectedResult.getNodeType(), result.getNodeType());
//		Assert.assertEquals(expectedResult.getMessageQueueName(),
//				result.getMessageQueueName());
	}

//	private PgraphManagerBean createPgraphManagerBean(PgraphDaoLocal pgraphDao)
//			throws Exception {
//		PgraphManagerBean bean = new PgraphManagerBean();
//
//		Field daoField = bean.getClass().getDeclaredField("pgraphDao");
//		daoField.setAccessible(true);
//		daoField.set(bean, pgraphDao);
//
//		return bean;
//	}

}

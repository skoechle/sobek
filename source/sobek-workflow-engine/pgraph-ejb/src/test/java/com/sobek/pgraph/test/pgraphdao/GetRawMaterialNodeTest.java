package com.sobek.pgraph.test.pgraphdao;


public class GetRawMaterialNodeTest{
    
//    @SuppressWarnings("unchecked")
//    @Test
//    public void validParamTest() throws Exception{
//	// Setup
//	long pgraphId = 1L;
//	RawMaterialEntity node = new RawMaterialEntity("nodeName");
//	
//	TypedQuery<RawMaterialEntity> query = Mockito.mock(TypedQuery.class);
//	Mockito.when(query.getSingleResult()).thenReturn(node);
//	
//	EntityManager em = Mockito.mock(EntityManager.class);
////	Mockito.when(em.createNamedQuery(PgraphEntity.GET_RAW_MATERIAL_QUERY, RawMaterialEntity.class)).thenReturn(query);
//	
//	PgraphDaoLocal dao = createPgraphDao(em);
//	
//	// Call the DAO
//	NodeEntity result = dao.getRawMaterialNode(pgraphId);
//	
//	// Check result
//	Assert.assertEquals(node, result);
//    }
    
    
//    @SuppressWarnings("unchecked")
//    @Test(expected = NoSuchPgraphException.class)
//    public void noSuchPgraphTest() throws Exception{
//	// Setup
//	long pgraphId = 1L;
//
//        TypedQuery<RawMaterialEntity> query = Mockito.mock(TypedQuery.class);
//	Mockito.when(query.getSingleResult()).thenThrow(NoResultException.class);
//	
//	TypedQuery<Long> existsQuery = Mockito.mock(TypedQuery.class);
//	Mockito.when(existsQuery.getSingleResult()).thenReturn(0L);
//	
//	EntityManager em = Mockito.mock(EntityManager.class);
////	Mockito.when(em.createNamedQuery(PgraphEntity.GET_RAW_MATERIAL_QUERY, RawMaterialEntity.class)).thenReturn(query);
//	Mockito.when(em.createNamedQuery(PgraphEntity.COUNT_BY_ID_QUERY, Long.class)).thenReturn(existsQuery);
//	
//	PgraphDaoLocal dao = createPgraphDao(em);
//
//	// Call the DAO. It should throw a NoSuchPgraphException
//	dao.getRawMaterialNode(pgraphId);
//    }
    
//    @SuppressWarnings("unchecked")
//    @Test(expected = InvalidPgraphStructureException.class)
//    public void noRawMaterialNode() throws Exception{
//	// Setup
//	long pgraphId = 1L;
//
//        TypedQuery<RawMaterialEntity> query = Mockito.mock(TypedQuery.class);
//	Mockito.when(query.getSingleResult()).thenThrow(NoResultException.class);
//	
//	TypedQuery<Long> existsQuery = Mockito.mock(TypedQuery.class);
//	Mockito.when(existsQuery.getSingleResult()).thenReturn(1L);
//	
//	EntityManager em = Mockito.mock(EntityManager.class);
////	Mockito.when(em.createNamedQuery(PgraphEntity.GET_RAW_MATERIAL_QUERY, RawMaterialEntity.class)).thenReturn(query);
//	Mockito.when(em.createNamedQuery(PgraphEntity.COUNT_BY_ID_QUERY, Long.class)).thenReturn(existsQuery);
//	
//	PgraphDaoLocal dao = createPgraphDao(em);
//
//	// Call the DAO. It should throw an InvalidPgraphStructureException.
//	dao.getRawMaterialNode(pgraphId);
//    }
    
//    @SuppressWarnings("unchecked")
//    @Test(expected = InvalidPgraphStructureException.class)
//    public void multipleRawMaterialNodes() throws Exception{
//	// Setup
//	long pgraphId = 1L;
//
//        TypedQuery<RawMaterialEntity> query = Mockito.mock(TypedQuery.class);
//	Mockito.when(query.getSingleResult()).thenThrow(NonUniqueResultException.class);
//		
//	EntityManager em = Mockito.mock(EntityManager.class);
////	Mockito.when(em.createNamedQuery(PgraphEntity.GET_RAW_MATERIAL_QUERY, RawMaterialEntity.class)).thenReturn(query);
//	
//	PgraphDaoLocal dao = createPgraphDao(em);
//
//	// Call the DAO. It should throw an InvalidPgraphStructureException.
//	dao.getRawMaterialNode(pgraphId);
//    }
//    
    
//    private PgraphDaoLocal createPgraphDao(EntityManager em) throws Exception{
//	PgraphDaoLocal dao = new PgraphDaoBean();
//   	
//   	Field emField = PgraphDaoBean.class.getDeclaredField("entityManager");
//   	emField.setAccessible(true);
//   	emField.set(dao, em);
//   	
//   	return dao;
//    }
}

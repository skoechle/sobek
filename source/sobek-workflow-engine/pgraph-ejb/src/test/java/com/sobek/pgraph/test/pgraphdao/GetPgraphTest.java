package com.sobek.pgraph.test.pgraphdao;


public class GetPgraphTest{
    
//    @Test
//    public void validParam() throws Exception{
//	// Setup
//	EntityManager entityManager = Mockito.mock(EntityManager.class);
//	
//	long pgraphId = 5;
//	PgraphEntity pgraphEntity = new PgraphEntity();
//	
//	Mockito.when(entityManager.find(PgraphEntity.class, pgraphId)).thenReturn(pgraphEntity);
//	
//	// Call the dao
//	PgraphDaoLocal dao = createPgraphDao(entityManager);
//	PgraphEntity result = dao.getPgraph(pgraphId);
//	
//	// Check result
//	Assert.assertEquals(pgraphEntity, result);
//    }
//    
//    @Test
//    public void noGraphFound() throws Exception{	
//	// Setup
//	EntityManager entityManager = Mockito.mock(EntityManager.class);
//	
//	long pgraphId = 5;
//	
//	Mockito.when(entityManager.find(PgraphEntity.class, pgraphId)).thenReturn(null);
//	
//	// Call the dao
//	PgraphDaoLocal dao = createPgraphDao(entityManager);
//	PgraphEntity result = dao.getPgraph(pgraphId);
//	
//	// Result should be null
//	Assert.assertNull(result);
//    }
//    
//    private PgraphDaoLocal createPgraphDao(EntityManager em) throws Exception{
//	PgraphDaoLocal dao = new PgraphDaoBean();
//	
//	Field emField = PgraphDaoBean.class.getDeclaredField("entityManager");
//	emField.setAccessible(true);
//	emField.set(dao, em);
//	
//	return dao;
//    }
}

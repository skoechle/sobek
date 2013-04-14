package com.sobek.pgraph.test.pgraphmanagerbean;

import java.lang.reflect.Field;

import org.junit.Test;
import org.mockito.Mockito;

import com.sobek.pgraph.PgraphDao;
import com.sobek.pgraph.PgraphManagerBean;
import com.sobek.pgraph.exception.NoSuchPgraphException;

public class GetReadyOperationsTest{

    @Test(expected = NoSuchPgraphException.class)
    public void pgraphIdDoesNotExist() throws Exception{
	long pgraphId = 3L;
	
	PgraphDao dao = Mockito.mock(PgraphDao.class);
	Mockito.when(dao.pgraphExists(pgraphId)).thenThrow(new NoSuchPgraphException("Test Exception."));
	
	PgraphManagerBean bean = createPgraphManagerBean(dao);
	
	bean.getReadyOperations(pgraphId);
    }
    
    private PgraphManagerBean createPgraphManagerBean(PgraphDao pgraphDao) throws Exception{
	PgraphManagerBean bean = new PgraphManagerBean();
	
	Field daoField = bean.getClass().getDeclaredField("pgraphDao");
	daoField.setAccessible(true);
	daoField.set(bean, pgraphDao);
	
	return bean;
    }
}

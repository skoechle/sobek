package com.sobek.workflow.engine;

import java.lang.reflect.Field;

import org.junit.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import com.sobek.workflow.WorkflowLocal;
import com.sobek.workflow.engine.entity.WorkflowData;

public class TestWorkflowEngineStartWorkflow {
	
	@Test
	public void nullParameters() throws Exception {
		String name = "foo";
		String parameters = null;
		WorkflowData data = new WorkflowData(name, parameters);

		WorkflowEngineDAOLocal dao = mock(WorkflowEngineDAOLocal.class);
		when(dao.create(name, parameters)).thenReturn(data);
		
		WorkflowLocal workflow = mock(WorkflowLocal.class);
		when(workflow.create(data)).thenReturn(true);
		
		
		WorkflowEngineBean bean = new WorkflowEngineBean();
		
		injectDao(bean, dao);
		injectWorkflow(bean, workflow);
		
		bean.startWorkflow(name, parameters);
		
		verify(dao,  times(1)).create(name, parameters);
		verify(workflow, times(1)).create(data);
		verify(workflow, times(1)).start(data);
		
	}
	
	private void injectDao(WorkflowEngineBean bean, WorkflowEngineDAOLocal dao) throws Exception {
		Field field = bean.getClass().getDeclaredField("dao");
		field.setAccessible(true);
		
		field.set(bean, dao);
	}
	
	private void injectWorkflow(WorkflowEngineBean bean, WorkflowLocal workflow) throws Exception {
		Field field = bean.getClass().getDeclaredField("workflow");
		field.setAccessible(true);
		
		field.set(bean, workflow);
	}

}

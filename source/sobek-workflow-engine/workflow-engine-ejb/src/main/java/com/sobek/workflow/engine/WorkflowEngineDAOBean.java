package com.sobek.workflow.engine;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sobek.workflow.engine.entity.WorkflowData;

@Stateless
public class WorkflowEngineDAOBean implements WorkflowEngineDAOLocal {
	private static Logger logger =
			Logger.getLogger(WorkflowEngine.class.getPackage().getName());
	@PersistenceContext(name="workflowEnginePU")
	private EntityManager manager;
	
	@Override
	public WorkflowData create(String name, Serializable parameters) {
		logger.log(
				Level.FINEST,
				"Creating a new workflow for name [{0}] and parameter type [{1}].",
				new Object[] {name, parameters});
		
		WorkflowData workflow = new WorkflowData(name, parameters);
		manager.persist(workflow);
		return workflow;
	}

	@Override
	public WorkflowData getWorkflow(long id) {
		
		WorkflowData data = manager.find(WorkflowData.class, id);
		return data;
	}

}

package com.sobek.workflow;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sobek.workflow.entity.WorkflowEntity;

@Stateless
public class WorkflowDAOBean implements WorkflowDAOLocal {
	private static Logger logger =
			Logger.getLogger(WorkflowDAOBean.class.getPackage().getName());
	@PersistenceContext(name="workflowEnginePU")
	private EntityManager manager;
	
	@Override
	public WorkflowEntity create(String name, Serializable parameters) {
		logger.log(
				Level.FINEST,
				"Creating a new workflow for name [{0}] and parameter type [{1}].",
				new Object[] {name, parameters});
		
		WorkflowEntity workflowData = new WorkflowEntity(name, parameters);
		this.manager.persist(workflowData);
		return workflowData;
	}

	@Override
	public WorkflowEntity getWorkflow(long id) {
		
		WorkflowEntity data = this.manager.find(WorkflowEntity.class, id);
		return data;
	}

	@Override
	public void update(WorkflowEntity data) {
		this.manager.persist(data);
	}
}

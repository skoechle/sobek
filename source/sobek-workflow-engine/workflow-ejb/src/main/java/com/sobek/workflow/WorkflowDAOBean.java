package com.sobek.workflow;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sobek.workflow.entity.WorkflowConfigurationEntity;
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
	public WorkflowEntity getWorkflow(String name) {
		WorkflowEntity entity = null;
		if(name != null) {
			Query query = this.manager.createNamedQuery(WorkflowEntity.GET_WORKFLOW_BY_NAME);
			query.setParameter(WorkflowEntity.NAME_PARAMETER, name);
			@SuppressWarnings("unchecked")
			List<WorkflowEntity> resultList = (List<WorkflowEntity>)query.getResultList();
			if(resultList != null && resultList.size() > 0) {
				entity = resultList.get(0);
			}
		}
		return entity;
	}

	@Override
	public void update(WorkflowEntity data) {
		this.manager.persist(data);
	}

	@Override
	public WorkflowConfigurationEntity findConfiguration(String name) {
		WorkflowConfigurationEntity returnValue = null;
		if(name != null) {
			Query query = this.manager.createNamedQuery(WorkflowConfigurationEntity.GET_CONFIG_BY_NAME);
			query.setParameter(WorkflowConfigurationEntity.NAME_PARAMETER, name);
			@SuppressWarnings("unchecked")
			List<WorkflowConfigurationEntity> resultList = (List<WorkflowConfigurationEntity>)query.getResultList();
			if(resultList != null && resultList.size() > 0) {
				returnValue = resultList.get(0);
			}
		}
		return returnValue;
	}

	@Override
	public void update(WorkflowConfigurationEntity entity) {
		this.manager.persist(entity);
	}
}

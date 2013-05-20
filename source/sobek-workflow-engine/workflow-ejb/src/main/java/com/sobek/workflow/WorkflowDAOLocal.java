package com.sobek.workflow;

import java.io.Serializable;

import javax.ejb.Local;

import com.sobek.workflow.entity.WorkflowConfigurationEntity;
import com.sobek.workflow.entity.WorkflowEntity;

@Local
public interface WorkflowDAOLocal {
	WorkflowEntity create(String name, Serializable parameters);
	
	WorkflowEntity getWorkflow(long id);

	WorkflowEntity getWorkflow(String name);

	void update(WorkflowEntity data);

	WorkflowConfigurationEntity findConfiguration(String name);

	void update(WorkflowConfigurationEntity entity);

	void create(WorkflowConfiguration config);
}

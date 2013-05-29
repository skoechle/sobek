package com.sobek.workflow;

import java.io.Serializable;

import javax.ejb.Local;

import com.sobek.workflow.entity.WorkflowDefinition;
import com.sobek.workflow.entity.WorkflowEntity;

@Local
public interface WorkflowDAOLocal {
	WorkflowEntity create(String name, Serializable parameters, long pgraphId);
	
	WorkflowEntity getWorkflow(long id);

	WorkflowEntity getWorkflow(String name);

	void update(WorkflowEntity data);

	WorkflowDefinition findConfiguration(String name);

	void update(WorkflowDefinition entity);

	void create(WorkflowConfiguration config);
}

package com.sobek.workflow;

import java.io.Serializable;

import javax.ejb.Local;

import com.sobek.workflow.entity.WorkflowEntity;

@Local
public interface WorkflowDAOLocal {
	WorkflowEntity create(String name, Serializable parameters);
	
	WorkflowEntity getWorkflow(long id);

	void update(WorkflowEntity data);
}

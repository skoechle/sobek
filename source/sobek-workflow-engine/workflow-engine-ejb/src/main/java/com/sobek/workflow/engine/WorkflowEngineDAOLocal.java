package com.sobek.workflow.engine;

import java.io.Serializable;

import javax.ejb.Local;

import com.sobek.workflow.engine.entity.WorkflowData;

@Local
public interface WorkflowEngineDAOLocal {
	WorkflowData create(String name, Serializable parameters);
	
	WorkflowData getWorkflow(long id);
}

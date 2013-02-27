package com.sobek.workflow.engine;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sobek.workflow.WorkflowLocal;
import com.sobek.workflow.engine.entity.WorkflowData;

@Stateless
public class WorkflowEngineBean implements WorkflowEngineLocal, WorkflowEngineRemote {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(WorkflowEngineBean.class.getPackage().getName());

	@EJB
	private WorkflowLocal workflow;
	
	@EJB
	private WorkflowEngineDAOLocal dao;

	@Override
	public long startWorkflow(String name, Serializable parameters) {
		logger.log(Level.FINEST, "Entering, name = [{0}], parameters = [{1}]", new Object[] {name, parameters});
		long returnValue = 0;
		WorkflowData data =
				this.dao.create(name, parameters);
		
		if(data != null) {
			boolean created = this.workflow.create(data);
			
			if(created) {
				this.workflow.start(data);
				returnValue = data.getId();
			} else {
				data.failed();
			}
		}
		
		return returnValue;
	}

	@Override
	public Serializable getParametersForWorkflow(long id) {
		Serializable returnValue = null;
		WorkflowData data =
				this.dao.getWorkflow(id);
		
		if(data != null) {
			returnValue = data.getParameters();
		}
		
		return returnValue;
	}
}

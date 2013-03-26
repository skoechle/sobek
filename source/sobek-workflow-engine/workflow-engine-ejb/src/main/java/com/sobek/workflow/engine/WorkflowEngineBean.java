package com.sobek.workflow.engine;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.sobek.pgraph.operation.Operation;
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
	public StartWorkflowResult startWorkflow(String name) {
		return this.startWorkflow(name, null);
	}

	@Override
	public StartWorkflowResult startWorkflow(String name, Serializable parameters) {
		logger.log(Level.FINEST, "Entering, name = [{0}], parameters = [{1}]", new Object[] {name, parameters});
		StartWorkflowResult returnValue = null;
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException(
					"A workflow cannot be started without the workflow name.  " +
					"The given name was [" + name + "]" );
		}

		WorkflowData data =
				this.dao.create(name, parameters);
		
		if(data != null) {
			returnValue = new StartWorkflowResult(data);
			
			boolean created = this.workflow.create(data);

			if(created) {
				List<Operation> operations = this.workflow.start(data);
				if(operations == null || operations.isEmpty()) {
					returnValue.failedToStart();
					data.failed();
				} else {
					//this.dao.storeOperations(data, operations);
				}
			} else {
				data.failed();
				returnValue.failedToCreateWorkflow();
			}
			
			this.dao.update(data);
		} else {
			returnValue = new StartWorkflowResult();
			returnValue.failedToCreateWorkflowData();
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

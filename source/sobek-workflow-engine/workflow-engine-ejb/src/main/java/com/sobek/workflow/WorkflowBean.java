package com.sobek.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import com.sobek.pgraph.operation.Operation;
import com.sobek.pgraph.operation.OperationState;
import com.sobek.workflow.engine.entity.WorkflowData;

@Stateless
public class WorkflowBean implements WorkflowLocal, WorkflowRemote {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Workflow.class.getPackage().getName());

	@Override
	public boolean create(WorkflowData data) {
		logger.log(
				Level.FINEST,
				"Creating a new workflow for data [{0}].",
				data);
		
		if(data == null) {
			throw new IllegalArgumentException(
					"The create method on class " + this.getClass().getName() +
					" cannot be called with null workflow data.");
		}
		
		return true;
	}

	@Override
	public List<Operation> start(WorkflowData data) {
		logger.log(
				Level.FINEST,
				"Starting workflow for data [{0}].",
				data);
		
		if(data == null) {
			throw new IllegalArgumentException(
					"The start method on class " + this.getClass().getName() +
					" cannot be called with null workflow data.");
		}
		
		Operation operation = new Operation() {
			
			@Override
			public void persist() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getJndiName() {
				// TODO Auto-generated method stub
				return "Operation Name";
			}
			
			@Override
			public OperationState getState() {
				// TODO Auto-generated method stub
				return OperationState.WORKING;
			}
		};
		List<Operation> list = new ArrayList<Operation>();
		list.add(operation);
		return list;
	}

}

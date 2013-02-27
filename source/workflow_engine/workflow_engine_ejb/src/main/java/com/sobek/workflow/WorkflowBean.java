package com.sobek.workflow;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

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
		
		return true;
	}

	@Override
	public boolean start(WorkflowData data) {
		logger.log(
				Level.FINEST,
				"Starting workflow for data [{0}].",
				data);
		return false;
	}

}

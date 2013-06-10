package com.sobek.workflow;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.sobek.client.operation.status.OperationCompletionMessage;
import com.sobek.client.operation.status.OperationStatusMessage;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.workflow.entity.WorkflowEntity;
import com.sobek.workflow.result.CreateWorkflowResult;
import com.sobek.workflow.result.StartWorkflowResult;

@Local
public interface WorkflowLocal extends Workflow {

	CreateWorkflowResult create(String name, Serializable parameters);
	StartWorkflowResult start(WorkflowEntity workflowEntity);
	void updateOperation(OperationStatusMessage status);
	List<OperationEntity> completeOperation(OperationCompletionMessage completion);
	void failOperation(WorkflowEntity entity, OperationEntity operation, String details);
	WorkflowEntity find(long workflowId);
}

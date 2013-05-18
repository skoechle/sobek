package com.sobek.workflow;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.sobek.client.operation.status.OperationCompletionMessage;
import com.sobek.client.operation.status.OperationStatusMessage;
import com.sobek.pgraph.entity.OperationEntity;
import com.sobek.workflow.entity.WorkflowEntity;

@Local
public interface WorkflowLocal extends Workflow {

	WorkflowEntity create(String name, Serializable parameters);
	List<OperationEntity> start(WorkflowEntity data);
	void update(OperationStatusMessage status);
	void complete(OperationCompletionMessage completion);
}

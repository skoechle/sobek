package com.sobek.workflow.engine;

import java.io.Serializable;

import javax.ejb.Local;
import javax.jms.Message;

import com.sobek.client.operation.OperationMessage;
import com.sobek.client.operation.status.OperationCompletionMessage;
import com.sobek.client.operation.status.OperationStatusMessage;

@Local
public interface WorkflowEngineLocal extends WorkflowEngine {

	void receiveOperationStatus(OperationStatusMessage status);

	void receiveOperationCompletion(OperationCompletionMessage status);

	void handleUnsupportedOperationMessage(OperationMessage operationMessage);

	void handleUnsupportedObjectType(Serializable object);

	void logMessageHandlingException(Message message, Exception e);

}

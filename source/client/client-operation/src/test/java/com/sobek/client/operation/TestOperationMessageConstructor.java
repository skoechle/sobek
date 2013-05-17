package com.sobek.client.operation;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestOperationMessageConstructor {
	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void validValues() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		
		OperationMessage operationStatus =
				new OperationMessage(workflowId, operationId);
		
		verifyValues(operationStatus, workflowId, operationId);
	}

	@Test
	public void minValues() {
		long workflowId = 1L;
		long operationId = 1L;
		
		OperationMessage operationStatus =
				new OperationMessage(workflowId, operationId);
		
		verifyValues(operationStatus, workflowId, operationId);
	}

	@Test
	public void maxValues() {
		long workflowId = Long.MAX_VALUE;
		long operationId = Long.MAX_VALUE;
		
		OperationMessage operationStatus =
				new OperationMessage(workflowId, operationId);
		
		verifyValues(operationStatus, workflowId, operationId);
	}

	@Test
	public void minMinusOneWorkflowId() {
		long workflowId = 0L;
		long operationId = 1L;
		
		expected.expect(IllegalArgumentException.class);
		new OperationMessage(workflowId, operationId);
	}

	@Test
	public void minMinusOneOperationId() {
		long workflowId = 1L;
		long operationId = 0L;
		
		expected.expect(IllegalArgumentException.class);
		new OperationMessage(workflowId, operationId);
	}

	private void verifyValues(OperationMessage operationStatus, long workflowId, long operationId) {
		assertEquals("The workflow identifier passed to the contructor was not returned by the getWorkflowId() method.", workflowId, operationStatus.getWorkflowId());
		assertEquals("The operation identifier passed to the contructor was not returned by the getOperationId() method.", operationId, operationStatus.getOperationId());
	}

}

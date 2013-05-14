package com.sobek.client.operation.status;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sobek.client.operation.status.OperationStatusMessage;
import com.sobek.client.operation.status.Status;

import static org.junit.Assert.assertEquals;

public class TestOperationStatusMessageConstructor {
	
	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void validValues() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		float percentComplete = 0.23F;
		Status status = Status.PROCESSING;
		String details = "Some details";
		
		OperationStatusMessage operationStatus =
				new OperationStatusMessage(workflowId, operationId, percentComplete, status, details);
		
		verifyValues(operationStatus, workflowId, operationId, percentComplete, status, details);
	}

	@Test
	public void unknownStatus() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		float percentComplete = 0.23F;
		Status status = Status.UNKNOWN;
		String details = "Some details";

		expected.expect(IllegalArgumentException.class);
		new OperationStatusMessage(workflowId, operationId, percentComplete, status, details);
	}

	@Test
	public void emptyDetails() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		float percentComplete = 0.23F;
		Status status = Status.PROCESSING;
		String details = "";
		
		OperationStatusMessage operationStatus =
				new OperationStatusMessage(workflowId, operationId, percentComplete, status, details);
		
		verifyValues(operationStatus, workflowId, operationId, percentComplete, status, details);
	}

	@Test
	public void nullDetails() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		float percentComplete = 0.23F;
		Status status = Status.PROCESSING;
		String details = null;
		
		OperationStatusMessage operationStatus =
				new OperationStatusMessage(workflowId, operationId, percentComplete, status, details);
		
		verifyValues(operationStatus, workflowId, operationId, percentComplete, status, "");
	}

	@Test
	public void minValues() {
		long workflowId = 1L;
		long operationId = 1L;
		float percentComplete = 0F;
		Status status = Status.PROCESSING;
		String details = "a";
		
		OperationStatusMessage operationStatus =
				new OperationStatusMessage(workflowId, operationId, percentComplete, status, details);
		
		verifyValues(operationStatus, workflowId, operationId, percentComplete, status, details);
	}

	@Test
	public void maxValues() {
		long workflowId = Long.MAX_VALUE;
		long operationId = Long.MAX_VALUE;
		float percentComplete = 1F;
		Status status = Status.PROCESSING;
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 1024; i++) {
			builder.append("a");
		}
		String details = builder.toString();
		
		OperationStatusMessage operationStatus =
				new OperationStatusMessage(workflowId, operationId, percentComplete, status, details);
		
		verifyValues(operationStatus, workflowId, operationId, percentComplete, status, details);
	}

	@Test
	public void minMinusOnePercentComplete() {
		long workflowId = 1L;
		long operationId = 1L;
		float percentComplete = -.001F;
		Status status = Status.PROCESSING;
		String details = "a";
		
		expected.expect(IllegalArgumentException.class);
		new OperationStatusMessage(workflowId, operationId, percentComplete, status, details);
	}

	@Test
	public void maxPlusOnePercentComplete() {
		long workflowId = Long.MAX_VALUE;
		long operationId = Long.MAX_VALUE;
		float percentComplete = 1.001F;
		Status status = Status.PROCESSING;
		String details = "a";
		
		expected.expect(IllegalArgumentException.class);
		new OperationStatusMessage(workflowId, operationId, percentComplete, status, details);
	}

	@Test
	public void maxPlusOneDetails() {
		long workflowId = Long.MAX_VALUE;
		long operationId = Long.MAX_VALUE;
		float percentComplete = 1F;
		Status status = Status.PROCESSING;
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 1025; i++) {
			builder.append("a");
		}
		String details = builder.toString();
		
		expected.expect(IllegalArgumentException.class);
		new OperationStatusMessage(workflowId, operationId, percentComplete, status, details);
	}

	private void verifyValues(OperationStatusMessage operationStatus, long workflowId,
			long operationId, float percentComplete, Status status,
			String details) {
		assertEquals("The workflow identifier passed to the contructor was not returned by the getWorkflowId() method.", workflowId, operationStatus.getWorkflowId());
		assertEquals("The operation identifier passed to the contructor was not returned by the getOperationId() method.", operationId, operationStatus.getOperationId());
		assertEquals("The percent complete passed to the contructor was not returned by the getPercentComplete() method.", percentComplete, operationStatus.getPercentComplete(), .1F);
		assertEquals("The status passed to the contructor was not returned by the getStatus() method.", status, operationStatus.getStatus());
		assertEquals("The details passed to the contructor were not returned by the getDetails() method.", details, operationStatus.getDetails());
	}
}

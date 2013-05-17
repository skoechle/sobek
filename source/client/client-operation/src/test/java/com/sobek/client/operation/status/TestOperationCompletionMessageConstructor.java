package com.sobek.client.operation.status;

import java.io.Serializable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sobek.client.operation.status.OperationCompletionMessage;
import com.sobek.client.operation.status.Status;

import static org.junit.Assert.assertEquals;

public class TestOperationCompletionMessageConstructor {
	
	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void validValues() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		Status status = Status.PROCESSING;
		String details = "Some details";
		Serializable material = "some bogus material that happens to be a string";
		
		OperationCompletionMessage operationStatus =
				new OperationCompletionMessage(workflowId, operationId, status, details, material);
		
		verifyValues(operationStatus, workflowId, operationId, status, details, material, true);
	}

	@Test
	public void emptyStringMaterial() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		Status status = Status.PROCESSING;
		String details = "Details";
		Serializable material = "";
		
		OperationCompletionMessage operationStatus =
				new OperationCompletionMessage(workflowId, operationId, status, details, material);
		
		verifyValues(operationStatus, workflowId, operationId, status, details, material, true);
	}

	@Test
	public void nullMaterial() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		Status status = Status.PROCESSING;
		String details = "Details";
		Serializable material = null;
		
		OperationCompletionMessage operationStatus =
				new OperationCompletionMessage(workflowId, operationId, status, details, material);
		
		verifyValues(operationStatus, workflowId, operationId, status, details, material, false);
	}

	private void verifyValues(
			OperationCompletionMessage operationCompletion,
			long workflowId,
			long operationId,
			Status status,
			String details,
			Serializable material,
			boolean hasMaterial) {
		assertEquals("The workflow identifier passed to the contructor was not returned by the getWorkflowId() method.", workflowId, operationCompletion.getWorkflowId());
		assertEquals("The operation identifier passed to the contructor was not returned by the getOperationId() method.", operationId, operationCompletion.getOperationId());
		assertEquals("The percent complete passed to the contructor was not returned by the getPercentComplete() method.", 1F, operationCompletion.getPercentComplete(), .1F);
		assertEquals("The status passed to the contructor was not returned by the getStatus() method.", status, operationCompletion.getStatus());
		assertEquals("The details passed to the contructor were not returned by the getDetails() method.", details, operationCompletion.getDetails());
		assertEquals("The material passed to the contructor was not returned by the getMaterial() method.", material, operationCompletion.getMaterial());
		assertEquals("The hasMaterial() method did not returned the expected value.", hasMaterial, operationCompletion.hasMaterial());
	}
}

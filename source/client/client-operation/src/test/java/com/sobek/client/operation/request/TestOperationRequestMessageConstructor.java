package com.sobek.client.operation.request;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestOperationRequestMessageConstructor {
	
	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void validValues() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		Serializable material = "some bogus material that happens to be a string";
		
		OperationRequestMessage operationStatus =
				new OperationRequestMessage(workflowId, operationId, material);
		
		verifyValues(operationStatus, workflowId, operationId, material);
	}

	@Test
	public void emptyStringMaterial() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		Serializable material = "";
		
		OperationRequestMessage operationStatus =
				new OperationRequestMessage(workflowId, operationId, material);
		
		verifyValues(operationStatus, workflowId, operationId, material);
	}

	@Test
	public void nullMaterial() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		Serializable material = null;

		expected.expect(IllegalArgumentException.class);
		new OperationRequestMessage(workflowId, operationId, material);
	}

	private void verifyValues(
			OperationRequestMessage operationCompletion,
			long workflowId,
			long operationId,
			Serializable material) {
		assertEquals("The workflow identifier passed to the contructor was not returned by the getWorkflowId() method.", workflowId, operationCompletion.getWorkflowId());
		assertEquals("The operation identifier passed to the contructor was not returned by the getOperationId() method.", operationId, operationCompletion.getOperationId());
		assertEquals("The material passed to the contructor was not returned by the getMaterial() method.", material, operationCompletion.getMaterial());
	}
}

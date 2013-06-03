package com.sobek.client.operation.status;

import java.io.Serializable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sobek.client.operation.status.OperationCompletionMessage;
import com.sobek.client.operation.status.CompletionState;

import static org.junit.Assert.assertEquals;

public class TestOperationCompletionMessageConstructor {
	
	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void validValues() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		CompletionState state = CompletionState.CANCELED;
		String details = "Some details";
		Serializable material = "some bogus material that happens to be a string";
		
		OperationCompletionMessage operationStatus =
				new OperationCompletionMessage(workflowId, operationId, material, state, details);
		
		verifyValues(operationStatus, workflowId, operationId, material, state, details);
	}

	@Test
	public void emptyStringMaterial() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		CompletionState state = CompletionState.CANCELED;
		String details = "Details";
		Serializable material = "";
		
		OperationCompletionMessage operationStatus =
				new OperationCompletionMessage(workflowId, operationId, material, state, details);
		
		verifyValues(operationStatus, workflowId, operationId, material, state, details);
	}

	@Test
	public void nullMaterial() {
		long workflowId = 2342343L;
		long operationId = 4234234L;
		CompletionState state = CompletionState.CANCELED;
		String details = "Details";
		Serializable material = null;

		expected.expect(IllegalArgumentException.class);
		new OperationCompletionMessage(workflowId, operationId, material, state, details);
	}

	private void verifyValues(
			OperationCompletionMessage operationCompletion,
			long workflowId,
			long operationId,
			Serializable material,
			CompletionState state,
			String details) {
		assertEquals("The workflow identifier passed to the contructor was not returned by the getWorkflowId() method.", workflowId, operationCompletion.getWorkflowId());
		assertEquals("The operation identifier passed to the contructor was not returned by the getOperationId() method.", operationId, operationCompletion.getOperationId());
		assertEquals("The state passed to the contructor was not returned by the getState() method.", state, operationCompletion.getState());
		assertEquals("The details passed to the contructor were not returned by the getDetails() method.", details, operationCompletion.getDetails());
		assertEquals("The material passed to the contructor was not returned by the getMaterial() method.", material, operationCompletion.getMaterialValue());
	}
}

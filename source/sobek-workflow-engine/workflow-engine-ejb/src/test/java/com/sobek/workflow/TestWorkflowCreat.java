package com.sobek.workflow;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestWorkflowCreat {

	
	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	@Test
	public void nullParameter() {
		WorkflowBean bean = new WorkflowBean();
	
		expected.expect(IllegalArgumentException.class);
		bean.create(null);
	}
	
}

package com.sobek.workflow.result;

import com.sobek.common.result.MessageCode;
import com.sobek.common.result.MessageCodeRange;

public class CreateWorkflowErrorCode extends MessageCode {
	private static final long serialVersionUID = 1L;
	private static final MessageCodeRange range = new MessageCodeRange(10000, 19999);

	private CreateWorkflowErrorCode(long code) {
		super(range, code);
	}

	public static final CreateWorkflowErrorCode EXCEPTION_THROWN = new CreateWorkflowErrorCode(10000);
	public static final CreateWorkflowErrorCode INVALID_CONFIGURATION = new CreateWorkflowErrorCode(10001);
	public static final CreateWorkflowErrorCode CONFIGURATION_NOT_FOUND = new CreateWorkflowErrorCode(10002);
	public static final CreateWorkflowErrorCode CREATION_FAILED = new CreateWorkflowErrorCode(10003);
}

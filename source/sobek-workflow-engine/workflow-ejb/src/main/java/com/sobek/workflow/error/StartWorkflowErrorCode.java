package com.sobek.workflow.error;

import com.sobek.common.result.MessageCode;
import com.sobek.common.result.MessageCodeRange;

public class StartWorkflowErrorCode extends MessageCode {
	private static final long serialVersionUID = 1L;
	private static final MessageCodeRange range = new MessageCodeRange(20000, 29999);

	private StartWorkflowErrorCode(long code) {
		super(range, code);
	}

	public static final StartWorkflowErrorCode EXCEPTION_THROWN = new StartWorkflowErrorCode(20000);
	public static final StartWorkflowErrorCode NO_OPERATIONS = new StartWorkflowErrorCode(20001);
}

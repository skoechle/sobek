package com.sobek.workflow.engine;

import com.sobek.common.result.MessageCode;
import com.sobek.common.result.MessageCodeRange;

public class StartWorkflowErrorCode extends MessageCode {
	private static final long serialVersionUID = 1L;
	private static final MessageCodeRange range = new MessageCodeRange(10000, 19999);

	private StartWorkflowErrorCode(long code) {
		super(range, code);
	}

	public static final StartWorkflowErrorCode FAILED_TO_CREATE_DATA = new StartWorkflowErrorCode(10001);
	public static final StartWorkflowErrorCode FAILED_TO_CREATE = new StartWorkflowErrorCode(10002);
	public static final StartWorkflowErrorCode FAILED_TO_START = new StartWorkflowErrorCode(10003);
}

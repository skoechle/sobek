package com.sobek.workflow.error;

import com.sobek.common.result.MessageCode;
import com.sobek.common.result.MessageCodeRange;

public class StartOperationErrorCode extends MessageCode {
	private static final long serialVersionUID = 1L;
	private static final MessageCodeRange range = new MessageCodeRange(30000, 39999);

	private StartOperationErrorCode(long code) {
		super(range, code);
	}

	public static final StartOperationErrorCode EXCEPTION_THROWN = new StartOperationErrorCode(30000);
}

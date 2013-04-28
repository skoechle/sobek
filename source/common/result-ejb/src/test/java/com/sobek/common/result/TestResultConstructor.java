package com.sobek.common.result;

import java.util.Iterator;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class TestResultConstructor {
	@Test
	public void test() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertFalse(errorIterator.hasNext());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertFalse(messageIterator.hasNext());

		Assert.assertTrue(result.successful());
		
		// No exception should be thrown when these methods are
		// called with empty lists.
		result.translate(Locale.CANADA);
		result.retranslate(Locale.CANADA);
	}
}

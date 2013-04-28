package com.sobek.common.result;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;

public class TestMessageConstructor {
	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void validValue() {
		MessageCode code = mock(MessageCode.class);
		Message message = new PublicMessage(code );
		
		Assert.assertSame(
				"The code passed to the constructor was not the code returned " +
				"from the getCode() method.",
				code,
				message.getCode());

		Assert.assertFalse(
				"The message was marked as translated after construction, " +
				"translate had not been called.",
				message.translated());
		
		Assert.assertEquals(
				"The message text was not empty after construction, " +
				"translate had not been called.",
				"",
				message.getText());
	}
	
	@Test
	public void nullCode() {
		expected.expect(IllegalArgumentException.class);
		new PublicMessage(null);
	}
	
	@Test
	public void nullValue() {
		expected.expect(IllegalArgumentException.class);
		new PublicMessage(null);
	}
}

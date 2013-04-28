package com.sobek.common.result;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

public class TestResultTranslate {
	@Test
	public void callTranslate() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};

		List<Message> errorMessages = new ArrayList<Message>();
		Message errorMessage = mock(Message.class);
		errorMessages.add(errorMessage);
		result.addError(errorMessage);

		Message errorMessage1 = mock(Message.class);
		errorMessages.add(errorMessage1);
		result.addError(errorMessage1);

		Message errorMessage2 = mock(Message.class);
		errorMessages.add(errorMessage2);
		result.addError(errorMessage2);

		Message errorMessage3 = mock(Message.class);
		errorMessages.add(errorMessage3);
		result.addError(errorMessage3);

		List<Message> messages = new ArrayList<Message>();
		Message message0 = mock(Message.class);
		messages.add(message0);
		result.addMessage(message0);

		Message message1 = mock(Message.class);
		messages.add(message1);
		result.addMessage(message1);

		Message message2 = mock(Message.class);
		messages.add(message2);
		result.addMessage(message2);

		Message message3 = mock(Message.class);
		messages.add(message3);
		result.addMessage(message3);
		
		Locale locale = Locale.ITALY;
		
		result.translate(locale);
		
		for(Message message : errorMessages) {
			verify(message, times(1)).translate(locale);
		}
		
		for(Message message : messages) {
			verify(message, times(1)).translate(locale);
		}
	}
}

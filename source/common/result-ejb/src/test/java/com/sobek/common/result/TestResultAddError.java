package com.sobek.common.result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import static org.mockito.Mockito.mock;

public class TestResultAddError {
	@Test
	public void addAnError() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};
		
		Message errorMessage = mock(Message.class);
		
		result.addError(errorMessage);
		
		Assert.assertFalse(result.successful());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertTrue(errorIterator.hasNext());
		Assert.assertSame(errorMessage, errorIterator.next());
		Assert.assertFalse(errorIterator.hasNext());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertFalse(messageIterator.hasNext());
	}
	
	@Test
	public void addNull() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};
		
		Message errorMessage = mock(Message.class);
		
		result.addError(null); // Nothing should happen
		result.addError(errorMessage);
		result.addError(null); // Nothing should happen
		
		Assert.assertFalse(result.successful());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertTrue(errorIterator.hasNext());
		Assert.assertSame(errorMessage, errorIterator.next());
		Assert.assertFalse(errorIterator.hasNext());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertFalse(messageIterator.hasNext());
	}
	
	@Test
	public void addSameErrorTwice() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};
		
		Message errorMessage = mock(Message.class);
		
		result.addError(errorMessage);
		result.addError(errorMessage);
		
		Assert.assertFalse(result.successful());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertTrue(errorIterator.hasNext());
		Assert.assertSame(errorMessage, errorIterator.next());
		Assert.assertFalse(errorIterator.hasNext());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertFalse(messageIterator.hasNext());
	}
	
	@Test
	public void addMultipleErrors() {
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

		
		Assert.assertFalse(result.successful());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertTrue(errorIterator.hasNext());
		Message message = errorIterator.next();
		Assert.assertTrue(errorMessages.contains(message));
		errorMessages.remove(message);

		Assert.assertTrue(errorIterator.hasNext());
		message = errorIterator.next();
		Assert.assertTrue(errorMessages.contains(message));
		errorMessages.remove(message);

		Assert.assertTrue(errorIterator.hasNext());
		message = errorIterator.next();
		Assert.assertTrue(errorMessages.contains(message));
		errorMessages.remove(message);

		Assert.assertTrue(errorIterator.hasNext());
		message = errorIterator.next();
		Assert.assertTrue(errorMessages.contains(message));
		errorMessages.remove(message);
		
		Assert.assertFalse(errorIterator.hasNext());
		Assert.assertTrue(errorMessages.isEmpty());

		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertFalse(messageIterator.hasNext());
	}
	
	@Test
	public void addMultipleErrorsMultipleTimes() {
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

		
		result.addError(errorMessage1);
		result.addError(errorMessage);
		result.addError(errorMessage3);
		result.addError(errorMessage);
		result.addError(errorMessage3);
		result.addError(errorMessage3);
		result.addError(errorMessage3);
		result.addError(errorMessage3);
		result.addError(errorMessage);
		result.addError(errorMessage);
		result.addError(errorMessage2);
		
		Assert.assertFalse(result.successful());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertTrue(errorIterator.hasNext());
		Message message = errorIterator.next();
		Assert.assertTrue(errorMessages.contains(message));
		errorMessages.remove(message);

		Assert.assertTrue(errorIterator.hasNext());
		message = errorIterator.next();
		Assert.assertTrue(errorMessages.contains(message));
		errorMessages.remove(message);

		Assert.assertTrue(errorIterator.hasNext());
		message = errorIterator.next();
		Assert.assertTrue(errorMessages.contains(message));
		errorMessages.remove(message);

		Assert.assertTrue(errorIterator.hasNext());
		message = errorIterator.next();
		Assert.assertTrue(errorMessages.contains(message));
		errorMessages.remove(message);
		
		Assert.assertFalse(errorIterator.hasNext());
		Assert.assertTrue(errorMessages.isEmpty());

		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertFalse(messageIterator.hasNext());
	}
}

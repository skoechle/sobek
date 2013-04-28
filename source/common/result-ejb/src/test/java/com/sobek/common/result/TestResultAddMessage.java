package com.sobek.common.result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import static org.mockito.Mockito.mock;

public class TestResultAddMessage {
	@Test
	public void addAMessage() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};
		
		Message message = mock(Message.class);
		
		result.addMessage(message);
		
		Assert.assertTrue(result.successful());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertTrue(messageIterator.hasNext());
		Assert.assertSame(message, messageIterator.next());
		Assert.assertFalse(messageIterator.hasNext());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertFalse(errorIterator.hasNext());
	}
	
	@Test
	public void addNull() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};
		
		Message message = mock(Message.class);
		
		result.addMessage(null); // Nothing should happen
		result.addMessage(message);
		result.addMessage(null); // Nothing should happen
		
		Assert.assertTrue(result.successful());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertTrue(messageIterator.hasNext());
		Assert.assertSame(message, messageIterator.next());
		Assert.assertFalse(messageIterator.hasNext());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertFalse(errorIterator.hasNext());
	}
	
	@Test
	public void addSameMessageTwice() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};
		
		Message message = mock(Message.class);
		
		result.addMessage(message);
		result.addMessage(message);
		
		Assert.assertTrue(result.successful());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertTrue(messageIterator.hasNext());
		Assert.assertSame(message, messageIterator.next());
		Assert.assertFalse(messageIterator.hasNext());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertFalse(errorIterator.hasNext());
	}
	
	@Test
	public void addMultipleMessages() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};

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

		
		Assert.assertTrue(result.successful());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertTrue(messageIterator.hasNext());
		Message message = messageIterator.next();
		Assert.assertTrue(messages.contains(message));
		messages.remove(message);

		Assert.assertTrue(messageIterator.hasNext());
		message = messageIterator.next();
		Assert.assertTrue(messages.contains(message));
		messages.remove(message);

		Assert.assertTrue(messageIterator.hasNext());
		message = messageIterator.next();
		Assert.assertTrue(messages.contains(message));
		messages.remove(message);

		Assert.assertTrue(messageIterator.hasNext());
		message = messageIterator.next();
		Assert.assertTrue(messages.contains(message));
		messages.remove(message);
		
		Assert.assertFalse(messageIterator.hasNext());
		Assert.assertTrue(messages.isEmpty());

		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertFalse(errorIterator.hasNext());
	}
	
	@Test
	public void addMultipleMessagesMultipleTimes() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};

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

		
		result.addMessage(message1);
		result.addMessage(message0);
		result.addMessage(message3);
		result.addMessage(message0);
		result.addMessage(message3);
		result.addMessage(message3);
		result.addMessage(message3);
		result.addMessage(message3);
		result.addMessage(message0);
		result.addMessage(message0);
		result.addMessage(message2);
		
		Assert.assertTrue(result.successful());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertTrue(messageIterator.hasNext());
		Message message = messageIterator.next();
		Assert.assertTrue(messages.contains(message));
		messages.remove(message);

		Assert.assertTrue(messageIterator.hasNext());
		message = messageIterator.next();
		Assert.assertTrue(messages.contains(message));
		messages.remove(message);

		Assert.assertTrue(messageIterator.hasNext());
		message = messageIterator.next();
		Assert.assertTrue(messages.contains(message));
		messages.remove(message);

		Assert.assertTrue(messageIterator.hasNext());
		message = messageIterator.next();
		Assert.assertTrue(messages.contains(message));
		messages.remove(message);
		
		Assert.assertFalse(messageIterator.hasNext());
		Assert.assertTrue(messages.isEmpty());

		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertFalse(errorIterator.hasNext());
	}
}

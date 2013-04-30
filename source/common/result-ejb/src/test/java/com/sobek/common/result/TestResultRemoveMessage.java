package com.sobek.common.result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import static org.mockito.Mockito.mock;

public class TestResultRemoveMessage {
	@Test
	public void removeAMessage() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};
		
		Message message = mock(Message.class);
		
		result.addMessage(message);
		result.removeMessage(message);

		Assert.assertTrue(result.successful());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertFalse(messageIterator.hasNext());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertFalse(errorIterator.hasNext());
	}
	
	@Test
	public void removeNull() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};
		
		Message message = mock(Message.class);
		
		result.addMessage(message);
		result.removeMessage(null); // Nothing should happen

		Assert.assertTrue(result.successful());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertTrue(messageIterator.hasNext());
		Assert.assertSame(message, messageIterator.next());
		Assert.assertFalse(messageIterator.hasNext());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertFalse(errorIterator.hasNext());
	}
	
	@Test
	public void removeMultipleMessages() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};

		List<Message> messages = new ArrayList<Message>();
		Message message0 = mock(Message.class);
		messages.add(message0);
		result.addMessage(message0);

		Message message1 = mock(Message.class);
		result.addMessage(message1);

		Message message2 = mock(Message.class);
		messages.add(message2);
		result.addMessage(message2);

		Message message3 = mock(Message.class);
		result.addMessage(message3);
		
		
		result.removeMessage(message3);
		result.removeMessage(message1);

		
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

		Assert.assertFalse(messageIterator.hasNext());
		Assert.assertTrue(messages.isEmpty());

		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertFalse(errorIterator.hasNext());
	}
	
	@Test
	public void removeAllMessages() {
		Result result = new Result() {
			private static final long serialVersionUID = 1L;
		};

		List<Message> messages = new ArrayList<Message>();
		Message message = mock(Message.class);
		messages.add(message);
		result.addMessage(message);

		Message message1 = mock(Message.class);
		messages.add(message1);
		result.addMessage(message1);

		Message message2 = mock(Message.class);
		messages.add(message2);
		result.addMessage(message2);

		Message message3 = mock(Message.class);
		messages.add(message3);
		result.addMessage(message3);

		
		result.removeMessage(message);
		result.removeMessage(message1);
		result.removeMessage(message3);
		result.removeMessage(message2);
		
		Assert.assertTrue(result.successful());
		
		Iterator<Message> messageIterator = result.getMessages();
		Assert.assertFalse(messageIterator.hasNext());
		
		Iterator<Message> errorIterator = result.getErrors();
		Assert.assertFalse(errorIterator.hasNext());
	}
}

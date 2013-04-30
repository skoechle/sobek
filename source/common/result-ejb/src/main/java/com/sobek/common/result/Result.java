package com.sobek.common.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public abstract class Result implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<Message> errors = new ArrayList<Message>();
	private List<Message> messages = new ArrayList<Message>();
	
	public boolean successful() {
		return this.errors.size() == 0;
	}
	
	public Iterator<Message> getErrors() {
		return this.errors.iterator();
	}
	
	protected void addError(Message error) {
		if(error != null && !this.errors.contains(error)) {
			this.errors.add(error);
		}
	}
	
	protected boolean removeError(Message error) {
		boolean returnValue = false;
		if(this.errors.contains(error)) {
			returnValue = this.errors.remove(error);
		}
		return returnValue;
	}
	
	public Iterator<Message> getMessages() {
		return this.messages.iterator();
	}
	
	protected void addMessage(Message message) {
		if(message != null && !this.messages.contains(message)) {
			this.messages.add(message);
		}
	}
	
	protected boolean removeMessage(Message message) {
		boolean returnValue = false;
		if(this.messages.contains(message)) {
			this.messages.remove(message);
		}
		return returnValue;
	}
	
	// package private
	synchronized void retranslate(Locale locale) {
		for(Message error : this.errors) {
			error.resetTranslation();
			error.translate(locale);
		}

		for(Message message : this.messages) {
			message.resetTranslation();
			message.translate(locale);
		}
	}
	
	// package private
	synchronized void translate(Locale locale) {
		for(Message error : this.errors) {
			error.translate(locale);
		}

		for(Message message : this.messages) {
			message.translate(locale);
		}
	}
}

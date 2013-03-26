package com.sobek.common.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public abstract class Result implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<Message> errors = new ArrayList<Message>();
	private List<Message> informationMessages = new ArrayList<Message>();
	
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
	
	protected void removeError(Message error) {
		if(error != null && this.errors.contains(error)) {
			this.errors.remove(error);
		}
	}
	
	protected void addInformation(Message information) {
		if(!this.informationMessages.contains(information)) {
			this.informationMessages.add(information);
		}
	}
	
	protected void removeInformation(Message information) {
		if(!this.informationMessages.contains(information)) {
			this.informationMessages.remove(information);
		}
	}
	
	// package private
	synchronized void retranslate(Locale locale) {
		for(Message error : this.errors) {
			error.resetTranslation();
			error.translate(locale);
		}

		for(Message message : this.informationMessages) {
			message.resetTranslation();
			message.translate(locale);
		}
	}
	
	// package private
	synchronized void translate(Locale locale) {
		for(Message error : this.errors) {
			error.translate(locale);
		}

		for(Message message : this.informationMessages) {
			message.translate(locale);
		}
	}
}

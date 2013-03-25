package com.sobek.common.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Result implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<Message> errors = new ArrayList<Message>();
	
	public boolean successful() {
		return this.errors.size() == 0;
	}
	
	public Iterator<Message> getErrors() {
		return this.errors.iterator();
	}
	
	protected void add(Message error) {
		if(error != null && !this.errors.contains(error)) {
			this.errors.add(error);
		}
	}
	
	protected void remove(Message error) {
		if(error != null && this.errors.contains(error)) {
			this.errors.remove(error);
		}
	}
	
	// package private
	synchronized void retranslateErrors(Locale locale) {
		for(Message error : this.errors) {
			error.resetTranslation();
			error.translate(locale);
		}
	}
	
	// package private
	synchronized void translateErrors(Locale locale) {
		for(Message error : this.errors) {
			error.translate(locale);
		}
	}
}

package com.sobek.common.result;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MessageCodeRange implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final Set<Long> registeredCodes = new HashSet<Long>();

	private Long start = 0L;
	private Long end = 0L;
	
	public MessageCodeRange(long start, long end) {
		if(start >= end) {
			throw new IllegalArgumentException(
					"An instance of " + this.getClass().getName() + " cannot be" +
					"created with a start value that is greater than or equal " +
					"to the end value.  The given values were:\n" +
					"Start = " + start + "\n" +
					"End   = " + end + "\n");
		}
		this.start = start;
		this.end = end;
	}
	
	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public boolean inRange(long code) {
		return code >= this.start && code <= this.end;
	}
	
	public boolean overlaps(MessageCodeRange range) {
		boolean overlapFound = inRange(range.start) || inRange(range.end);
		
		if(!overlapFound) {
			overlapFound = range.start <= this.start && range.end >= this.end;
		}
		return overlapFound;
	}
	
	public boolean isCodeRegistered(long code) {
		return this.registeredCodes.contains(code);
	}
	
	// Package Private
	boolean registerCode(long code) {
		boolean returnValue = false;
		if(this.inRange(code)) {
			returnValue = this.registeredCodes.add(code); 
		}

		return returnValue;
	}
	
	// Package Private
	Iterator<Long> getRegisteredCodes() {
		return this.registeredCodes.iterator();
	}
	
	@Override
	public int hashCode() {
		return this.start.hashCode() ^ this.end.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean returnValue = false;
		
		if(obj instanceof MessageCodeRange) {
			MessageCodeRange compare = (MessageCodeRange)obj;
			
			returnValue = this.start == compare.start && this.end == compare.end;
		}

		return returnValue;
	}
	
	
	@Override
	public String toString() {
		return this.start + ":" + this.end;
	}
}

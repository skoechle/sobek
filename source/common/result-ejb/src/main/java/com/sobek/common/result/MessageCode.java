package com.sobek.common.result;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class MessageCode implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long code;
	private MessageCodeRange range = null;
	
	protected MessageCode(MessageCodeRange range, long code) {
		this.range = range;
		
		registerRange(range);

		if(this.range == null) {

			throw new IllegalArgumentException(
					"An " + MessageCode.class + " cannot be creted with a null " +
					"range.  The given range was [" + range + "].");
		}
		
		if(!validForRange(code)) {
			throw new IllegalArgumentException(
					"The code [" + code + "] is not valid for range [" + range + "].");
		}
	}

	public long getValue() {
		return this.code;
	}
	
	
	protected abstract MessageCodeRange getRangeForClass();

	private boolean validForRange(long code) {
		return this.range.inRange(code);
	}

	private static Set<MessageCodeRange> ranges = new HashSet<MessageCodeRange>();
	
	protected static void registerRange(MessageCodeRange range) {
		if(range != null && !ranges.contains(range)) {
			for(MessageCodeRange existingRange : ranges) {
				if(existingRange.overlaps(range)) {
					throw new IllegalArgumentException(
							"The new range [" + range + "] overlaps existing " +
							"range [" + existingRange + "].");
				}
			}
			
			ranges.add(range);
		}
	}
	
	public static Iterator<MessageCodeRange> getRegisteredRanges() {
		return ranges.iterator();
	}
}

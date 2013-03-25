package com.sobek.common.result;

import java.io.Serializable;

public class MessageCodeRange implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long start = 0L;
	private Long end = 0L;
	
	public boolean inRange(long code) {
		return code >= this.start && code <= this.end;
	}
	
	public boolean overlaps(MessageCodeRange range) {
		return inRange(range.start) || inRange(range.end);
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

package com.sobek.common.result;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestMessageCodeRangeConstructor {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void validValuesPositive() {
		long start = 3L;
		long end = 283749837L;
		
		MessageCodeRange range = new MessageCodeRange(start, end);
		
		Assert.assertEquals(start, range.getStart());
		Assert.assertEquals(end, range.getEnd());
	}

	@Test
	public void validValuesNegative() {
		long start = -323424242323L;
		long end = -283749837L;
		
		MessageCodeRange range = new MessageCodeRange(start, end);
		
		Assert.assertEquals(start, range.getStart());
		Assert.assertEquals(end, range.getEnd());
	}

	@Test
	public void validValuesMixed() {
		long start = -323424242323L;
		long end = 283749837L;
		
		MessageCodeRange range = new MessageCodeRange(start, end);
		
		Assert.assertEquals(start, range.getStart());
		Assert.assertEquals(end, range.getEnd());
	}

	@Test
	public void validValuesPositiveZeroStart() {
		long start = 0;
		long end = 283749837L;
		
		MessageCodeRange range = new MessageCodeRange(start, end);
		
		Assert.assertEquals(start, range.getStart());
		Assert.assertEquals(end, range.getEnd());
	}

	@Test
	public void validValuesNegativeZeroEnd() {
		long start = -283749837L;
		long end = 0;
		
		MessageCodeRange range = new MessageCodeRange(start, end);
		
		Assert.assertEquals(start, range.getStart());
		Assert.assertEquals(end, range.getEnd());
	}

	@Test
	public void validValuesMaxValues() {
		long start = Long.MIN_VALUE;
		long end = Long.MAX_VALUE;
		
		MessageCodeRange range = new MessageCodeRange(start, end);
		
		Assert.assertEquals(start, range.getStart());
		Assert.assertEquals(end, range.getEnd());
	}

	@Test
	public void validValuesSeperatedByOnePositive() {
		long start = 283749836L;
		long end = 283749837L;
		
		MessageCodeRange range = new MessageCodeRange(start, end);
		
		Assert.assertEquals(start, range.getStart());
		Assert.assertEquals(end, range.getEnd());
	}

	@Test
	public void validValuesSeperatedByOneNegative() {
		long start = -323424242323L;
		long end = -323424242322L;
		
		MessageCodeRange range = new MessageCodeRange(start, end);
		
		Assert.assertEquals(start, range.getStart());
		Assert.assertEquals(end, range.getEnd());
	}

	@Test
	public void validValuesSeperatedByOneZeroStart() {
		long start = 0L;
		long end = 1L;
		
		MessageCodeRange range = new MessageCodeRange(start, end);
		
		Assert.assertEquals(start, range.getStart());
		Assert.assertEquals(end, range.getEnd());
	}

	@Test
	public void validValuesSeperatedByOneZeroEnd() {
		long start = -1L;
		long end = 0L;
		
		MessageCodeRange range = new MessageCodeRange(start, end);
		
		Assert.assertEquals(start, range.getStart());
		Assert.assertEquals(end, range.getEnd());
	}

	@Test
	public void startAfterEndPositive() {
		long start = 283749837L;
		long end = 23432;
		
		this.exception.expect(IllegalArgumentException.class);
		new MessageCodeRange(start, end);
	}

	@Test
	public void startAfterEndNegative() {
		long start = -283L;
		long end = -23432L;
		
		this.exception.expect(IllegalArgumentException.class);
		new MessageCodeRange(start, end);
	}

	@Test
	public void startAfterEndMixed() {
		long start = 283L;
		long end = -23432L;
		
		this.exception.expect(IllegalArgumentException.class);
		new MessageCodeRange(start, end);
	}

	@Test
	public void startAfterEndZeroEnd() {
		long start = 283L;
		long end = 0;
		
		this.exception.expect(IllegalArgumentException.class);
		new MessageCodeRange(start, end);
	}

	@Test
	public void startAfterEndZeroStart() {
		long start = 0;
		long end = -214332L;
		
		this.exception.expect(IllegalArgumentException.class);
		new MessageCodeRange(start, end);
	}

	@Test
	public void startAfterEndMaxStart() {
		long start = Long.MAX_VALUE;
		long end = 0;
		
		this.exception.expect(IllegalArgumentException.class);
		new MessageCodeRange(start, end);
	}

	@Test
	public void startAfterEndMinEnd() {
		long start = 0;
		long end = Long.MIN_VALUE;
		
		this.exception.expect(IllegalArgumentException.class);
		new MessageCodeRange(start, end);
	}

	@Test
	public void startEqualsEnd() {
		long start = 234324L;
		
		this.exception.expect(IllegalArgumentException.class);
		new MessageCodeRange(start, start);
	}
}

package com.sobek.common.result;

import junit.framework.Assert;

import org.junit.Test;

public class TestMessageCodeRangeInRange {
	@Test
	public void valuesPositive() {
		long start = 3L;
		long end = 283749837L;
		
		testInRangeMethod(start, end);
	}

	@Test
	public void valuesNegative() {
		long start = -323424242323L;
		long end = -283749837L;
		
		testInRangeMethod(start, end);
	}

	@Test
	public void valuesMixed() {
		long start = -323424242323L;
		long end = 283749837L;
		
		testInRangeMethod(start, end);
	}

	@Test
	public void valuesPositiveZeroStart() {
		long start = 0;
		long end = 283749837L;
		
		testInRangeMethod(start, end);
	}

	@Test
	public void valuesNegativeZeroEnd() {
		long start = -283749837L;
		long end = 0;
		
		testInRangeMethod(start, end);
	}

	@Test
	public void valuesMaxValues() {
		long start = Long.MIN_VALUE;
		long end = Long.MAX_VALUE;
		
		MessageCodeRange range = new MessageCodeRange(start, end);

		long testValue = start;
		Assert.assertTrue(range.inRange(testValue));

		testValue = start + 1;
		Assert.assertTrue(range.inRange(testValue));

		testValue = (end - start)/2;
		Assert.assertTrue(range.inRange(testValue));

		testValue = start + (long)((end - start)/2);
		Assert.assertTrue(range.inRange(testValue));

		testValue = end;
		Assert.assertTrue(range.inRange(testValue));

		testValue = end - 1;
		Assert.assertTrue(range.inRange(testValue));
	}

	@Test
	public void valuesSeperatedByOnePositive() {
		long start = 283749836L;
		long end = 283749837L;
		
		testInRangeMethod(start, end);
	}

	@Test
	public void valuesSeperatedByOneNegative() {
		long start = -323424242323L;
		long end = -323424242322L;
		
		testInRangeMethod(start, end);
	}

	@Test
	public void valuesSeperatedByOneZeroStart() {
		long start = 0L;
		long end = 1L;
		
		testInRangeMethod(start, end);
	}

	@Test
	public void valuesSeperatedByOneZeroEnd() {
		long start = -1L;
		long end = 0L;
		
		testInRangeMethod(start, end);
	}

	private void testInRangeMethod(long start, long end) {
		MessageCodeRange range = new MessageCodeRange(start, end);

		long testValue = start;
		Assert.assertTrue(range.inRange(testValue));

		testValue = start + 1;
		Assert.assertTrue(range.inRange(testValue));

		testValue = start + (long)((end - start)/2);
		Assert.assertTrue(range.inRange(testValue));

		testValue = end;
		Assert.assertTrue(range.inRange(testValue));

		testValue = end - 1;
		Assert.assertTrue(range.inRange(testValue));

		testValue = start - 1;
		Assert.assertFalse(range.inRange(testValue));

		testValue = end + 1;
		Assert.assertFalse(range.inRange(testValue));

		testValue = Long.MIN_VALUE;
		Assert.assertFalse(range.inRange(testValue));

		testValue = Long.MAX_VALUE;
		Assert.assertFalse(range.inRange(testValue));
	}
}

package com.sobek.common.result;

import junit.framework.Assert;

import org.junit.Test;

public class TestMessageCodeRangeOverlaps {
	@Test
	public void valuesPositive() {
		long start = 3L;
		long end = 283749837L;
		
		testOverlapsMethod(start, end);
	}

	@Test
	public void valuesNegative() {
		long start = -323424242323L;
		long end = -283749837L;
		
		testOverlapsMethod(start, end);
	}

	@Test
	public void valuesMixed() {
		long start = -323424242323L;
		long end = 283749837L;
		
		testOverlapsMethod(start, end);
	}

	@Test
	public void valuesPositiveZeroStart() {
		long start = 0;
		long end = 283749837L;
		
		testOverlapsMethod(start, end);
	}

	@Test
	public void valuesNegativeZeroEnd() {
		long start = -283749837L;
		long end = 0;
		
		testOverlapsMethod(start, end);
	}

	@Test
	public void valuesMaxValues() {
		long start = Long.MIN_VALUE;
		long end = Long.MAX_VALUE;
		
		MessageCodeRange range = new MessageCodeRange(start, end);

		MessageCodeRange testValue = new MessageCodeRange(start, end);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start + 1, end);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start + (long)((end - start)/2), end);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(end - 1, end);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start, end - 1);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start, start + 1);
		Assert.assertTrue(range.overlaps(testValue));
	}

	@Test
	public void valuesSeperatedByOnePositive() {
		long start = 283749836L;
		long end = 283749837L;
		
		testOverlapsMethod(start, end);
	}

	@Test
	public void valuesSeperatedByOneNegative() {
		long start = -323424242323L;
		long end = -323424242322L;
		
		testOverlapsMethod(start, end);
	}

	@Test
	public void valuesSeperatedByOneZeroStart() {
		long start = 0L;
		long end = 1L;
		
		testOverlapsMethod(start, end);
	}

	@Test
	public void valuesSeperatedByOneZeroEnd() {
		long start = -1L;
		long end = 0L;
		
		testOverlapsMethod(start, end);
	}

	private void testOverlapsMethod(long start, long end) {
		MessageCodeRange range = new MessageCodeRange(start, end);

		MessageCodeRange testValue = new MessageCodeRange(start, end);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start, end + 1);
		Assert.assertTrue(range.overlaps(testValue));

		if(start + 1 != end) {
			testValue = new MessageCodeRange(start + 1, end);
			Assert.assertTrue(range.overlaps(testValue));
		}

		testValue = new MessageCodeRange(start + 1, end + 1);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start + (long)((end - start)/2), end);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start + (long)((end - start)/2), end + 1);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(end, end + 1);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(end - 1, end);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(end - 1, end + 1);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start - 1, end);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start - 1, end + 1);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(Long.MIN_VALUE, end);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(Long.MIN_VALUE, Long.MAX_VALUE);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(end, Long.MAX_VALUE);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(end + 1, Long.MAX_VALUE);
		Assert.assertFalse(range.overlaps(testValue));

		testValue = new MessageCodeRange(start - 1, end);
		Assert.assertTrue(range.overlaps(testValue));

		if(start !=  end - 1) {
			testValue = new MessageCodeRange(start, end - 1);
			Assert.assertTrue(range.overlaps(testValue));
		}

		testValue = new MessageCodeRange(start - 1, end - 1);
		Assert.assertTrue(range.overlaps(testValue));

		if( ((long)((end - start)/2)) != 0) {
			testValue = new MessageCodeRange(start, start + (long)((end - start)/2));
			Assert.assertTrue(range.overlaps(testValue));
		}

		testValue = new MessageCodeRange(start - 1, start + (long)((end - start)/2));
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start - 1, start);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start, start + 1);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start - 1, start + 1);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start, end + 1);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(start, Long.MAX_VALUE);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(Long.MIN_VALUE, start);
		Assert.assertTrue(range.overlaps(testValue));

		testValue = new MessageCodeRange(Long.MIN_VALUE, start - 1);
		Assert.assertFalse(range.overlaps(testValue));
	}
}

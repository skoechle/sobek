package com.sobek.common.result;

import junit.framework.Assert;

import org.junit.Test;

public class TestMessageCodeRangeRegisterCode {
	@Test
	public void valuesPositive() {
		long start = 3L;
		long end = 283749837L;
		
		testRegisterCodeMethodWithValidValues(start, end);
	}

	@Test
	public void valuesNegative() {
		long start = -323424242323L;
		long end = -283749837L;
		
		testRegisterCodeMethodWithValidValues(start, end);
	}

	@Test
	public void valuesMixed() {
		long start = -323424242323L;
		long end = 283749837L;
		
		testRegisterCodeMethodWithValidValues(start, end);
	}

	@Test
	public void valuesPositiveZeroStart() {
		long start = 0;
		long end = 283749837L;
		
		testRegisterCodeMethodWithValidValues(start, end);
	}

	@Test
	public void valuesNegativeZeroEnd() {
		long start = -283749837L;
		long end = 0;
		
		testRegisterCodeMethodWithValidValues(start, end);
	}

	@Test
	public void valuesMaxValues() {
		long start = Long.MIN_VALUE;
		long end = Long.MAX_VALUE;
		
		testRegisterCodeMethodWithValidValues(start, end);
	}

	@Test
	public void valuesSeperatedByOnePositive() {
		long start = 283749836L;
		long end = 283749837L;
		
		testRegisterCodeMethodWithValidValues(start, end);
	}

	@Test
	public void valuesSeperatedByOneNegative() {
		long start = -323424242323L;
		long end = -323424242322L;
		
		testRegisterCodeMethodWithValidValues(start, end);
	}

	@Test
	public void valuesSeperatedByOneZeroStart() {
		long start = 0L;
		long end = 1L;
		
		testRegisterCodeMethodWithValidValues(start, end);
	}

	@Test
	public void valuesSeperatedByOneZeroEnd() {
		long start = -1L;
		long end = 0L;
		
		testRegisterCodeMethodWithValidValues(start, end);
	}

	private void testRegisterCodeMethodWithValidValues(long start, long end) {
		MessageCodeRange range = new MessageCodeRange(start, end);

		long testValue = start;
		Assert.assertTrue(range.registerCode(testValue));
		Assert.assertTrue(range.isCodeRegistered(testValue));
		Assert.assertFalse(range.registerCode(testValue));

		testValue = start + 1;
		Assert.assertTrue(range.registerCode(testValue));
		Assert.assertTrue(range.isCodeRegistered(testValue));
		Assert.assertFalse(range.registerCode(testValue));

		if((long)((end - start)/2) != 0) {
			testValue = start + (long)((end - start)/2);
			Assert.assertTrue(range.registerCode(testValue));
			Assert.assertTrue(range.isCodeRegistered(testValue));
			Assert.assertFalse(range.registerCode(testValue));
		}

		if(start + 1 != end) {
			testValue = end;
			Assert.assertTrue(range.registerCode(testValue));
			Assert.assertTrue(range.isCodeRegistered(testValue));
			Assert.assertFalse(range.registerCode(testValue));
		}
		
		if(end - 1 != start) {
			testValue = end - 1;
			Assert.assertTrue(range.registerCode(testValue));
			Assert.assertTrue(range.isCodeRegistered(testValue));
			Assert.assertFalse(range.registerCode(testValue));
		}

		if(start != Long.MIN_VALUE) {
			testValue = start - 1;
			Assert.assertFalse(range.registerCode(testValue));
			Assert.assertFalse(range.isCodeRegistered(testValue));

			testValue = Long.MIN_VALUE;
			Assert.assertFalse(range.registerCode(testValue));
			Assert.assertFalse(range.isCodeRegistered(testValue));
		}

		if(end != Long.MAX_VALUE) {
			testValue = end + 1;
			Assert.assertFalse(range.registerCode(testValue));
			Assert.assertFalse(range.isCodeRegistered(testValue));

			testValue = Long.MAX_VALUE;
			Assert.assertFalse(range.registerCode(testValue));
			Assert.assertFalse(range.isCodeRegistered(testValue));
		}
	}
}

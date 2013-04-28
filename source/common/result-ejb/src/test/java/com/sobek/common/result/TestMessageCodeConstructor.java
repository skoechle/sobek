package com.sobek.common.result;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestMessageCodeConstructor {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void resetStatics() throws Exception {
		Field field = MessageCode.class.getDeclaredField("ranges");
		
		field.setAccessible(true);
		
		field.set(null, new HashSet<MessageCodeRange>());
	}
	
	@Test
	public void positiveCodeValid() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = 1;

		MessageCode messageCode = new PublicMessageCode(range, code);
		
		validateMessageCode(messageCode, range, code);
		
		code = 83;
		messageCode = new PublicMessageCode(range, code);
		validateMessageCode(messageCode, range, code);

		code = 100;
		messageCode = new PublicMessageCode(range, code);
		validateMessageCode(messageCode, range, code);
	}
	
	@Test
	public void zeroValid() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = 0;

		MessageCode messageCode = new PublicMessageCode(range, code);
		
		validateMessageCode(messageCode, range, code);
	}
	
	@Test
	public void negativeCodeValid() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -1;

		MessageCode messageCode = new PublicMessageCode(range, code);
		
		validateMessageCode(messageCode, range, code);
		
		code = -23;
		messageCode = new PublicMessageCode(range, code);
		validateMessageCode(messageCode, range, code);

		code = -100;
		messageCode = new PublicMessageCode(range, code);
		validateMessageCode(messageCode, range, code);
	}
	
	@Test
	public void positiveCodeInvalid() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = 101;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void positiveCodeInvalid1() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = 101;
		
		code = 183;
		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void zeroInvalid() {
		MessageCodeRange range = new MessageCodeRange(-100, -1);
		long code = 0;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void negativeCodeInvalid() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -101;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void negativeCodeInvalid1() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -101;
		
		code = -123;
		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void positiveCodeAlreadyAdded() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = 100;

		new PublicMessageCode(range, code);

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void positiveCodeAlreadyAdded1() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);

		long code = 62;

		new PublicMessageCode(range, code);

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void positiveCodeAlreadyAdded2() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);

		long code = 1;

		new PublicMessageCode(range, code);

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void zeroAlreadyAdded() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = 0;

		new PublicMessageCode(range, code);

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void negativeCodeAlreadyAdded() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);

	}
	
	@Test
	public void negativeCodeAlreadyAdded1() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -73;

		new PublicMessageCode(range, code);

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);

	}
	
	@Test
	public void negativeCodeAlreadyAdded2() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -1;

		new PublicMessageCode(range, code);

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}

	
	@Test
	public void nullRange() {
		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(null, 1);
	}
	
	@Test
	public void rangesDontOverlapPositive() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(101, 250000);
		code = 23434;

		MessageCode messageCode = new PublicMessageCode(range, code);
		validateMessageCode(messageCode, range, code);
	}
	
	@Test
	public void rangesDontOverlapZero() {
		MessageCodeRange range = new MessageCodeRange(-1, 0);
		long code = 0;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(1, 250000);
		code = 23434;

		MessageCode messageCode = new PublicMessageCode(range, code);
		validateMessageCode(messageCode, range, code);
	}
	
	@Test
	public void rangesDontOverlapNegative() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, -101);
		code = -23434;

		MessageCode messageCode = new PublicMessageCode(range, code);
		validateMessageCode(messageCode, range, code);
	}
	
	@Test
	public void rangesOverlapPositive() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(0, 250000);
		code = 23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapPositive1() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(1, 250000);
		code = 23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapPositive2() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(23, 250000);
		code = 23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapPositive3() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(99, 250000);
		code = 23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapPositive4() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(100, 250000);
		code = 23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapZero() {
		MessageCodeRange range = new MessageCodeRange(-1, 0);
		long code = 0;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(0, 1);
		code = 1;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapZero1() {
		MessageCodeRange range = new MessageCodeRange(-1, 0);
		long code = 0;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-1, 1);
		code = 1;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapZero2() {
		MessageCodeRange range = new MessageCodeRange(-1, 1);
		long code = 0;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-1, 0);
		code = -1;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapZero3() {
		MessageCodeRange range = new MessageCodeRange(-1, 0);
		long code = 0;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-2, 0);
		code = -1;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapZero4() {
		MessageCodeRange range = new MessageCodeRange(-1, 0);
		long code = 0;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(0, 2);
		code = 1;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapNegative() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, -100);
		code = -23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapNegative1() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, -99);
		code = -23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapNegative2() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, -34);
		code = -23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapNegative3() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, -1);
		code = -23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapNegative4() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, 0);
		code = -23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapNegative5() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, 1);
		code = -23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapNegative6() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, 73);
		code = -23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapNegative7() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, 99);
		code = -23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapNegative8() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, 100);
		code = -23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangesOverlapNegative9() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = -100;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-250000, 101);
		code = -23434;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangeContainsPrevious1() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = 0;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-101, 101);
		code = 1;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}
	
	@Test
	public void rangeContainsPrevious2() {
		MessageCodeRange range = new MessageCodeRange(-100, 100);
		long code = 0;

		new PublicMessageCode(range, code);

		range = new MessageCodeRange(-1234, 234340);
		code = -1;

		exception.expect(IllegalArgumentException.class);
		new PublicMessageCode(range, code);
	}

	private void validateMessageCode(
			MessageCode messageCode,
			MessageCodeRange range,
			long code) {
		
		Assert.assertEquals(code, messageCode.getValue());
		
		Iterator<MessageCodeRange> iterator = MessageCode.getRegisteredRanges();

		boolean foundRange = false;
		while(iterator.hasNext()) {
			MessageCodeRange nextRange = iterator.next();
			if(nextRange.equals(range)) {
				foundRange = true;
				break;
			}
		}

		Assert.assertTrue("The range [" + range + "] was not returned in the registered ranges.", foundRange);
		
		Assert.assertTrue("The constructor did not reject code [" + code + "] which is not in range [" + range + "].", range.inRange(code));
	}

}

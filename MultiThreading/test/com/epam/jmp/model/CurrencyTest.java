package com.epam.jmp.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CurrencyTest {

	Currency c1, c2;
	
	@Before
	public void setUp() throws Exception {
		c1 = Currency.EURO;
		c2 = Currency.EURO;
	}


	@Test
	public void testResolveByName() {
		//fail("Not yet implemented");
		Currency byr = Currency.resolveByName("BYR");
		assertEquals(byr, Currency.BYR);
		assertNotSame(Currency.RUR, byr);
	}

	@Test
	public void testConvertCurrencyCurrencyFloat() {
		float convertResult = Currency.convert(c1, c2, 10);
		assertTrue(10==convertResult);
	}

	@Test
	public void testConvertStringStringFloat() {
		float convertResult = Currency.convert("EURO", "BYR", 10);
		float convertResultFail = Currency.convert("CHF", "BYR", 10);
		assertTrue(135657.692f==convertResult);
		assertTrue(convertResultFail<0);
	}

}

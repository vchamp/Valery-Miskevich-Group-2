package com.epam.jmp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.epam.jmp.model.Currency;

public class ProtocolTest {

	private Protocol protocol;

	@Before
	public void setUp() throws Exception {

		protocol = new Protocol();
	}
	
	@Test
	public void testProcessInput() {
		String result = protocol.processInput(null);
		assertEquals("Welcome! $$ Enter as: client [1], office [2]", result);
		
		result = protocol.processInput("1");
		assertEquals("Enter your name:", result);
	
		result = protocol.processInput("quit");
		assertEquals("Bye.", result);
	}

	
}

package com.epam.jmp.io;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.epam.jmp.model.Account;
import com.epam.jmp.model.Currency;

public class IOTest {

	@Test
	public void testWrite() {
		Account a = new Account("Test", 12345, Currency.BYR);
		String fileName = IO.get().write(a);
		System.out.println(fileName);
		assertNotEquals(fileName, "No name");
	}

	@Test
	public void testRead() {
		Account resultAccount = IO.get().read("Test");
		assertNotNull(resultAccount);
		assertTrue(resultAccount.getBallance() == 12345);
		assertTrue(resultAccount.getCurrency() == Currency.BYR);
	}

	@Test
	public void testAccountList() {
		List<String> accountList = IO.get().accountList();
		assertTrue(accountList.contains("Test"));
	}

	@Test
	public void testDelete() {

		boolean result = IO.get().delete("Test");
		assertTrue(result);
	}

}

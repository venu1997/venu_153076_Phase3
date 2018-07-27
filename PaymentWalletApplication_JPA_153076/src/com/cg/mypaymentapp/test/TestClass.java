package com.cg.mypaymentapp.test;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cg.mypaymentapp.beans.Customer;

import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.JPAUtil;
import com.cg.mypaymentapp.service.WalletService;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class TestClass {

	private static WalletService service; 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		service=new WalletServiceImpl();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		EntityManager entityManager=JPAUtil.getEntityManager();
		String query1="truncate table account_details_paytm";
		String query2="truncate table Wallet_account_details";
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery(query1).executeUpdate();
		entityManager.createNativeQuery(query2).executeUpdate();
		entityManager.getTransaction().commit();
	}

	@Before
	public void setUp() throws Exception 
	{

			 
		
		
		 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAccount() 
	{
		service.createAccount("Venu", "7036071319",new BigDecimal(9000));
		service.createAccount("Shankar", "8008985352",new BigDecimal(6000));
		Customer customer=service.createAccount("Prasad", "7702420100",new BigDecimal(7000));
		assertNotNull(customer);
	}
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount1() 
	{
		service.createAccount(null, "9177424331", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount2() 
	{
		service.createAccount("", "8008732530", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount3() 
	{
		service.createAccount("Madhu", "999", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount4() 
	{
		service.createAccount("Madhu", "", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount5() 
	{
		service.createAccount("", "", new BigDecimal(1500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount6() 
	{
		service.createAccount("Venu", "7036071319", new BigDecimal(9000));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testCreateAccount7() 
	{
		service.createAccount("Srinu", "6549871230", new BigDecimal(-100));
	}
	
	

	@Test(expected=InvalidInputException.class)
	public void testShowBalance8() 
	{
		service.showBalance(null);		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance9() 
	{
		service.showBalance("");		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance10() 
	{
		service.showBalance("12345");		
	}
	
	
	
	
	@Test(expected=InvalidInputException.class)
	public void testShowBalance11() 
	{
		service.showBalance("70360713192");		
	}
	


	

	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer12() 
	{
		service.fundTransfer("7036071319", "", new BigDecimal(0));		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer13() 
	{
		service.fundTransfer("", "7702420100", new BigDecimal(500));		
	}
	

	@Test(expected=InvalidInputException.class)
	public void testFundTransfer14() 
	{
		service.fundTransfer("", "7702420100", new BigDecimal(-100));		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer15() 
	{
		service.fundTransfer("", "", new BigDecimal(500));		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer16() 
	{
		service.fundTransfer(null, null, new BigDecimal(0));		
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testFundTransfer17() 
	{
		service.fundTransfer("7702420100", null, new BigDecimal(50));		
	}
	
	


	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount18() 
	{
		service.depositAmount(null, new BigDecimal(500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount19() 
	{
		service.depositAmount("", new BigDecimal(500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount20() 
	{
		service.depositAmount("6382163801", new BigDecimal(500));
	}
	
	
	@Test(expected=InvalidInputException.class)
	public void testDepositAmount21() 
	{
		service.depositAmount("7702420100", new BigDecimal(-1000));
	}
	
	

	
	
	@Test(expected=InvalidInputException.class)
	public void testWithdrawAmount22() 
	{
		service.withdrawAmount("7036071319", new BigDecimal(0));
	}
	

}

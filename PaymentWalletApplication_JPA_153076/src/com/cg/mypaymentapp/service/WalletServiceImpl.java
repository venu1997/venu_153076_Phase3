
package com.cg.mypaymentapp.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;


public class WalletServiceImpl implements WalletService
{

    private WalletRepo repo;

	
	public WalletServiceImpl(){
		repo= new WalletRepoImpl();
	}
	
	

	
	

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) 
	{
		
		Customer customer=null;
		
		if(isValidName(name) && isValidMobile(mobileNo) && isValidamount(amount))
		{
		customer=new Customer(name,mobileNo,new Wallet(amount));
		if(repo.findOne(mobileNo) != null)
			throw new InvalidInputException("The account with mobile Number "+ mobileNo+" is already created");
		repo.beginTransaction();
		repo.save(customer);
		repo.commitTransaction();
		}
		
		return customer;		
	}

	public Customer showBalance(String mobileNo) throws InvalidInputException
	{
		
		Customer customer=null;
		if(isValidMobile(mobileNo))
		{
			repo.beginTransaction();
		  customer=repo.findOne(mobileNo);
		  repo.commitTransaction();
		}
		if(customer == null)
			throw new InvalidInputException("The mobile Number You Entered is Not having Payment Wallet Account");
		
		return customer;
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InsufficientBalanceException,InvalidInputException
	{
		
		Customer source=null;
		Customer target=null;
		if(isValidMobile(sourceMobileNo) && isValidMobile(targetMobileNo) && isValidamount(amount))
		{
			     
			     if(sourceMobileNo.equals(targetMobileNo))
			    	 throw new  InvalidInputException("Enter Different Accounts to transfer Money");
			     
			     if(amount.compareTo(new BigDecimal(0)) == 0 )
			    	 throw new InvalidInputException("Enter valid Amount to transfer");
		         source=repo.findOne(sourceMobileNo);
		         
		         if(source == null)
		        	 throw new InvalidInputException("There is No Payment wallet account for the Number "+sourceMobileNo);
		         
	             target=repo.findOne(targetMobileNo);
	             
	             if(target == null)
	            	 throw new InvalidInputException("There is No Payment wallet account for the Number "+targetMobileNo);
		
		if(amount.compareTo(source.getWallet().getBalance()) > 0 )
			throw new InsufficientBalanceException("Insufficient Balance in the account "+sourceMobileNo);
		
		
		source=withdrawAmount(sourceMobileNo, amount);
		target=depositAmount(targetMobileNo, amount);
		
		}
		return source;
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException
	{
		
		Customer customer=null;
		if(isValidMobile(mobileNo) && isValidamount(amount))
		{
			repo.beginTransaction();
		customer=repo.findOne(mobileNo);
		
		if(customer == null)
			throw new InvalidInputException("There is No Payment wallet account for the Number "+mobileNo);
		
		if(amount.equals(new BigDecimal(0)))
			throw new InvalidInputException("Enter Valid Amount to Withdraw");
		
		BigDecimal balance=customer.getWallet().getBalance().add(amount);
		customer.setWallet(new Wallet(balance));
		
		repo.save(customer);
		repo.saveTransactions(mobileNo,new Date(),"d",amount);
		repo.commitTransaction();
		}
		
		return customer;
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InsufficientBalanceException,InvalidInputException
	{
		Customer customer=null;
		if(isValidMobile(mobileNo) && isValidamount(amount))
		{
			if(amount.equals(new BigDecimal(0)))
				throw new InvalidInputException("Enter Valid Amount to Withdraw");
			repo.beginTransaction();
		 customer=repo.findOne(mobileNo);
		 
		 if(customer == null)
				throw new InvalidInputException("There is No Payment wallet account for the Number "+mobileNo);
		
		if(amount.compareTo(customer.getWallet().getBalance()) > 0 )
			throw new InsufficientBalanceException("Insufficient Balance");
		
		BigDecimal balance=customer.getWallet().getBalance().subtract(amount);
		customer.setWallet(new Wallet(balance));
		repo.save(customer);
		repo.saveTransactions(mobileNo,new Date(),"w",amount);
		repo.commitTransaction();
				
	}
		return customer;
	
}

	@Override
	public List<String> printTransactions(String mobileNo)
	{
		List<String> transHistory=new ArrayList<String>();
		if(!isValidMobile(mobileNo))
			throw new InvalidInputException("There is no account with this mobile number "+mobileNo);
		    repo.beginTransaction();
			transHistory=repo.printTransactions(mobileNo);
			if(transHistory.isEmpty())
				throw new InvalidInputException("No Transactions made");
			repo.commitTransaction();
		return transHistory;
	}



public boolean isValidName(String name) throws InvalidInputException 
{
	if( name == null)
		throw new InvalidInputException( "Sorry, Customer Name is null" );
	
	if( name.trim().isEmpty() )
		throw new InvalidInputException( "Sorry, customer Name is Empty" );
	
	return true;
}

public boolean isValidMobile(String mobileNo)throws InvalidInputException
{
	if( mobileNo == null ||  isPhoneNumberInvalid( mobileNo ))
		throw new InvalidInputException( "Sorry, Phone Number "+mobileNo+" is invalid"  );
	
	return true;
}

public boolean isValidamount(BigDecimal amount)throws InvalidInputException
{
	if( amount == null || isAmountInvalid( amount ) )
		throw new InvalidInputException( "Amount is invalid" );

	return true;
}


public boolean isAmountInvalid(BigDecimal amount) 
{
	
	if( amount.compareTo(new BigDecimal(0)) < 0) 
	{
		return true;
	}		
	else 
		return false;
}

public static boolean isPhoneNumberInvalid( String phoneNumber )
{
	if(String.valueOf(phoneNumber).matches("[1-9][0-9]{9}")) 
	{
		return false;
	}		
	else 
		return true;
}

public void delete(String mobileNo)
{
	repo.delete(mobileNo);
}
}
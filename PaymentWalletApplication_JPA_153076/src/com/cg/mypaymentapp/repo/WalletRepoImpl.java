package com.cg.mypaymentapp.repo;

import java.sql.Connection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.cg.mypaymentapp.beans.*;
import com.cg.mypaymentapp.exception.InvalidInputException;

public class WalletRepoImpl implements WalletRepo{

	private EntityManager entityManager;
	
	public WalletRepoImpl() 
	{
		entityManager=JPAUtil.getEntityManager();
	}

	@Override
	public boolean save(Customer customer) 
	    {
		   
		   Customer customer1=entityManager.find(Customer.class, customer.getMobileNo());
			
		   if(customer1 == null)
			   entityManager.merge(customer);
		   else
			   entityManager.persist(customer);
		return true;
			
	
	
    
	    }

	@Override
	public Customer findOne(String mobileNo) {
		Customer customer=null;
		customer=entityManager.find(Customer.class,mobileNo);
		return customer;
	}
	
	public void delete(String mobileNo)
	{
		Customer customer=entityManager.find(Customer.class, mobileNo);
		entityManager.getTransaction().begin();
		entityManager.remove(customer);
		entityManager.getTransaction().commit();
	}

	@Override
	public void saveTransactions(String mobileNo, Date date, String type, BigDecimal amount) {
		TransactionHistory transactionHistory=new TransactionHistory(mobileNo,date,type,amount);
		
		entityManager.persist(transactionHistory);
	}

	@Override
	public List<String> printTransactions(String mobileNo) {
		List<TransactionHistory> historyList=new ArrayList<>();
		List<String> history=new ArrayList<>();
		String entry=null;
		String query="select t from TransactionHistory t where t.mobileNo=:mob";
		historyList=entityManager.createQuery(query,TransactionHistory.class).setParameter("mob", mobileNo).getResultList();
		
		for (TransactionHistory transactionHistory : historyList) 
		{
			if(transactionHistory.getType().equals("d"))
				history.add("Rupees "+transactionHistory.getAmount()+" is Deposited on "+transactionHistory.getDate());
			else
				history.add("Rupees "+transactionHistory.getAmount()+" is withdrawn on "+transactionHistory.getDate());
		}
		
		return history;
	}

	@Override
	public void beginTransaction() {
		entityManager.getTransaction().begin();
		
	}

	@Override
	public void commitTransaction() {
		entityManager.getTransaction().commit();
		
		
	}
}

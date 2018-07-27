package com.cg.mypaymentapp.beans;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="paytm_transaction_histories")
public class TransactionHistory {
	private String mobileNo;
	private Date transaction_date;
	private String type;
	private BigDecimal amount;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int transactionId;
	public TransactionHistory() 
	{
		
	}
	public TransactionHistory(String mobileNo, Date date, String type, BigDecimal amount) {
		super();
		this.mobileNo = mobileNo;
		this.transaction_date = date;
		this.type = type;
		this.amount = amount;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Date getDate() {
		return transaction_date;
	}
	public void setDate(Date date) {
		this.transaction_date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}

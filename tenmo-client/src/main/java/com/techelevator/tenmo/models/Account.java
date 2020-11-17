package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Account {
	private Long accountId;
	private Long userId;
	private BigDecimal accountBalance;
	
	public Account() {
		
	}
	
	public Account(Long accountId, Long userId, BigDecimal accountBalance) {
		this.accountId = accountId;
		this.userId = userId;
		this.accountBalance = accountBalance;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "\n--------------------------" +
			   "\n Account Details" +
			   "\n--------------------------" +
			   "\n Account ID: " + accountId +
			   "\n User ID: " + userId +
			   "\n Account Balance: " + accountBalance;
				
	}
	

}

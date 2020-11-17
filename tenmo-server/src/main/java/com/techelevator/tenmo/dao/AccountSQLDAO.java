package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.exception.BalanceOverdrawException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@Component
public class AccountSQLDAO implements AccountDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public AccountSQLDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Account viewCurrentBalance(Long accountId) {
		Account accounts = null;
		String sql = "SELECT * FROM accounts WHERE account_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
		while(results.next()) {
			accounts = mapRowToAccount(results);
		}
		return accounts;	
	}
	
	@Override
	public Account updateBalance(Long accountId, Account updatedAccount) {
		String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
		jdbcTemplate.update(sql, updatedAccount.getAccountBalance(), accountId);
		
		return updatedAccount;
	}

	private Account mapRowToAccount(SqlRowSet rs) {
		Account account = new Account();
		account.setAccountId(rs.getLong("account_id"));
		account.setUserId(rs.getLong("user_id"));
		account.setAccountBalance(rs.getBigDecimal("balance"));
		return account;
	}

	/*
	@Override
	public Account updateSenderAccountBalance(Long fromAccountId, Account updatedFromAccount) {  
		
		BigDecimal currentAmount = null;
		String sqlSelect = "SELECT balance FROM accounts WHERE account_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelect, updatedFromAccount.getAccountId());
		if(results.next()) {
			currentAmount = results.getBigDecimal("balance");
		}
		
		BigDecimal updatedAmount = currentAmount.subtract(updatedFromAccount.getAccountBalance());
		String sqlUpdate = "UPDATE accounts SET balance = ? WHERE account_id = ?";
		jdbcTemplate.update(sqlUpdate, updatedAmount, fromAccountId);
		
		return updatedFromAccount;
		
		
		BigDecimal account = accountBalance;
		BigDecimal sub = amount;
		if((account.compareTo(sub) == - 1)) {
			return false;
		}
		String sql = "UPDATE accounts SET account_balance = ? WHERE accountId = ?";
		BigDecimal diff = account.subtract(sub);
		return jdbcTemplate.update(sql, diff) == 1;
		
	}
	
	@Override
	public Account updateReceiverAccountBalance(Long toAccountId, Account updatedToAccount) { 
		BigDecimal currentAmount = null;
		String sqlSelect = "SELECT balance FROM accounts WHERE account_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelect, updatedToAccount.getAccountId());
		if(results.next()) {
			currentAmount = results.getBigDecimal("balance");
		}
		
		BigDecimal updatedAmount = currentAmount.add(updatedToAccount.getAccountBalance());
		String sqlUpdate = "UPDATE accounts SET balance = ? WHERE account_id = ?";
		jdbcTemplate.update(sqlUpdate, updatedAmount, toAccountId);
		
		return updatedToAccount;
	}
	
	*/
	
	/*
	@Override
	public boolean updateReceiverAccountBalance(Long accountId, BigDecimal amount, BigDecimal accountBalance) { 
		String sql = "UPDATE accounts SET account_balance = ? WHERE accountId = ?";
		BigDecimal account = accountBalance;
		BigDecimal add = amount;
		BigDecimal sum = account.add(add);
		return jdbcTemplate.update(sql, sum) == 1;
	}
	*/



}
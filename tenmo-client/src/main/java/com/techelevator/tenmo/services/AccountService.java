package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;

public class AccountService {

	public static String AUTH_TOKEN = "";
	private final String BASE_URL;
	public RestTemplate restTemplate = new RestTemplate();
	
	public AccountService(String url) {
		BASE_URL = url;
	}
	
	public Account viewCurrentBalance(Long accountId) throws AccountServiceException {
		Account account = null;
		try {
			account = restTemplate.exchange(BASE_URL + "accounts/" + accountId, HttpMethod.GET, makeAuthEntity(), Account.class).getBody();
		} catch (RestClientResponseException ex) {
			throw new AccountServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
		return account;
	}
	
	public Account updateBalance(Long accountId, BigDecimal amount) throws AccountServiceException {
		Account account = new Account(accountId, accountId, amount);
		
		try {
			account = restTemplate.exchange(BASE_URL + "accounts/" + accountId, HttpMethod.PUT, makeAccountEntity(account), Account.class).getBody();
		} catch (RestClientResponseException ex) {
			throw new AccountServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
		return account;
	}
	
	private HttpEntity<Account> makeAccountEntity(Account account) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity<Account> entity = new HttpEntity<>(account, headers);
		return entity;
	}
	
	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
	
	//Old code, to be deleted:
	
	/*
	public Account updateSenderAccountBalance(Long fromUserId, BigDecimal amount) throws AccountServiceException {
		Account account = new Account (fromUserId, fromUserId, amount);
		try {
			account = restTemplate.exchange(BASE_URL + "accounts/" + fromUserId, HttpMethod.PUT, makeAccountEntity(account), Account.class).getBody();
		} catch (RestClientResponseException ex) {
			throw new AccountServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
		return account;
	}
	
	public Account updateReceiverAccountBalance(Long toUserId, BigDecimal amount) throws AccountServiceException {
		Account account = new Account (toUserId, toUserId, amount);
		try {
			account = restTemplate.exchange(BASE_URL + "accounts/" + toUserId, HttpMethod.PUT, makeAccountEntity(account), Account.class).getBody();
		} catch (RestClientResponseException ex) {
			throw new AccountServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
		return account;
	}
	*/
}

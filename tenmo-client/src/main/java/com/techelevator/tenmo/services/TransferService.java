package com.techelevator.tenmo.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;

public class TransferService {
	
	public static String AUTH_TOKEN = ""; 
	private final String BASE_URL;
	private final RestTemplate restTemplate = new RestTemplate();
	
	Account account;
	
	public TransferService(String url) {
		BASE_URL = url;
	}

	public boolean viewTransferHistory(Long accountId) throws TransferServiceException {
		boolean hasHistory = false;
		
		Transfer[] transfers = null;
		try {
			transfers = restTemplate.exchange(BASE_URL + "accounts/"+ accountId +"/transfers", 
					HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
		
			if (transfers.length == 0 || transfers == null) {
				return hasHistory;
			
			} else {
				hasHistory = true;
				
				System.out.println("----------------------------------------------");
				String heading1 = "Transfers ID";
				String heading2 = "From/To";
				String heading3 = "Amount";
				System.out.printf( "%-15s %10s %15s %n", heading1, heading2, heading3);
				System.out.println("----------------------------------------------");
				
				for(int i = 0; i < transfers.length; i++) {
					if(transfers[i].getAccountFrom() == accountId){
						System.out.printf("%-15s %10s %15s %n",transfers[i].getTransferId(), "To: "+transfers[i].getToUsername(), "$"+transfers[i].getAmount());
					
					} else if (transfers[i].getAccountTo() == accountId){
						System.out.printf("%-15s %10s %15s %n",transfers[i].getTransferId(), "From: "+transfers[i].getFromUsername(), "$"+transfers[i].getAmount());
					}
					
				}
				System.out.println("---------");
				return hasHistory;
				
			}
		} catch (RestClientResponseException ex) {
			throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
	
	}
	
	public Transfer viewTransferDetails(Long accountId, Long transferId) throws TransferServiceException {
		
		Transfer transfer = null;
		try {
			transfer = restTemplate.exchange(BASE_URL + "transfers/"+ transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
			if (transfer != null && (transfer.getAccountFrom() == accountId || transfer.getAccountTo() == accountId)) {
				return transfer;
			} else {
				return null;
			}
		} catch (RestClientResponseException ex) {
			throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
		
	}
	
	public Transfer sendBucks(Long fromUser, Long toUser, BigDecimal amount) throws TransferServiceException {
		Long transferTypeId = (long)2;
		Long transferStatusId = (long)2;
		
		Transfer transfer = new Transfer(transferTypeId, transferStatusId, fromUser, toUser, amount);
		try {
			transfer = restTemplate.exchange(BASE_URL + "transfers", HttpMethod.POST, makeTransferEntity(transfer), Transfer.class).getBody();
		} catch (RestClientResponseException ex) {
			throw new TransferServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
		}
		return transfer;
	}
	
	private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
		return entity;
	}
	
	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
	
	//Old code to be deleted:
	/*
	private Transfer makeTransfer(String CSV) {
		
		String[] parsed = CSV.split(", ");
		Long transferId = (long)1;
		Long transferTypeId = (long)2;
		Long transferStatusId = (long)2;
		BigDecimal transferAmount = new BigDecimal(parsed.length - 1);
		
		
		if(parsed.length < 7 || parsed.length > 8) {
			return null;
		}
		
		
		if (parsed.length == 7) {
			String[] withId = new String[9];
			String[] idArray = new String[] {new Random().nextInt(1000) + ""};
			System.arraycopy(idArray, 0, withId, 0, 1);
			System.arraycopy(parsed, 0, withId, 1, 5);
			parsed = withId;
			
		}
		return new Transfer(Long.parseLong(parsed[0].trim()), Long.parseLong(parsed[1].trim()), parsed[2].trim(), Long.parseLong(parsed[3].trim()), parsed[4].trim(), Long.parseLong(parsed[5].trim()), Long.parseLong(parsed[6].trim()), transferAmount);
		
		
		if (parsed.length == 6) {
			String[] withId = new String[7];
			String[] idArray = new String[] {new Random().nextInt(1000) + ""};
			System.arraycopy(idArray, 0, withId, 0, 1);
			System.arraycopy(parsed, 0, withId, 1, 5);
			parsed = withId;
			
		}
		
		
		
		return new Transfer(transferTypeId, transferStatusId, Long.parseLong(parsed[0].trim()), Long.parseLong(parsed[1].trim()), transferAmount);
	}
	*/
	
}

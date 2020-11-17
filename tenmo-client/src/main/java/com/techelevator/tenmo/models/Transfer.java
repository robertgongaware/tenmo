package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfer {
	
	private Long transferId;
	private Long transferTypeId;
	private String transferType;
	private Long transferStatusId;
	private String transferStatus;
	private Long accountFrom;
	private Long accountTo;
	private BigDecimal amount;
	
	private String fromUsername;
	private String toUsername;
	
	public Transfer() {
		
	}
	
	/*
	public Transfer(Long transferId, Long transferTypeId, String transferType, Long transferStatusId, String transferStatus,
			Long accountFrom,Long accountTo, BigDecimal amount) {
		this.transferId = transferId;
		this.transferTypeId = transferTypeId;
		this.transferType = transferType;
		this.transferStatusId = transferStatusId;
		this.transferStatus = transferStatus;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
	}
	*/
	
	public Transfer(Long transferTypeId, Long transferStatusId, Long accountFrom, Long accountTo, BigDecimal amount) {
		this.transferTypeId = transferTypeId;
		this.transferStatusId = transferStatusId;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
	}
	
	public Long getTransferId() {
		return transferId;
	}
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}
	
	public Long getTransferTypeId() {
		return transferTypeId;
	}
	public void setTransferTypeId(Long transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	
	public Long getTransferStatusId() {
		return transferStatusId;
	}
	public void setTransferStatusId(Long transferStatusId) {
		this.transferStatusId = transferStatusId;
	}
	
	public String getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}
	public Long getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(Long accountFrom) {
		this.accountFrom = accountFrom;
	}

	public Long getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(Long accountTo) {
		this.accountTo = accountTo;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	
	public String getToUsername() {
		return toUsername;
	}
	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}
	
	@Override
	public String toString() {
		return "\n--------------------------------" +
			   "\n Transfer Details" +
			   "\n--------------------------------" +
			   "\n Transfer ID: " + transferId +
			   "\n From: " + fromUsername +
			   "\n To: " + toUsername +
			   "\n Type: " + transferType +
			   "\n Status: " + transferStatus +
			   "\n Amount: $" + amount;
	}

}

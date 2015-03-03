package com.ng.mats.psa.mt.pocketmoni.model;

public class MoneyTransfer {

	private String sender;
	private String receiver;
	private String transactionType;
	private long amount;
	private String mmo;
	private String teasypin;
	private String teasyrequestreference;
	private String bankAccountNumber;
	private String bankSortCode;
	private String agentMSISDN;
	private String subscriberMSISDN;
	private String terminalId;
	private String masterKey;
	private String samplePin;

	// @XmlTransient
	// private CashOutStatus status = CashOutStatus.PENDING;
	private String reference;

	public MoneyTransfer(String sender, String receiver,
			String transactionType, long amount, String mmo, String reference,
			String teasypin) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.transactionType = transactionType;
		this.amount = amount;
		this.mmo = mmo;
		this.teasypin = teasypin;
		this.teasyrequestreference = "201407010000001";
		// this.status = status;
		this.reference = reference;
	}

	public MoneyTransfer() {

		this.sender = "";
		this.receiver = "";
		this.transactionType = "";
		this.amount = 0;
		this.mmo = "";
		this.teasypin = "";
		this.teasyrequestreference = "";
		// this.status = status;
		this.reference = "";

	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getMmo() {
		return mmo;
	}

	public void setMmo(String mmo) {
		this.mmo = mmo;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	// public CashOutStatus getStatus() {
	// return status;
	// }
	//
	// public void setStatus(CashOutStatus status) {
	// this.status = status;

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTeasypin() {
		return teasypin;
	}

	public void setTeasypin(String teasypin) {
		this.teasypin = teasypin;
	}

	public String getTeasyrequestreference() {
		return teasyrequestreference;
	}

	public void setTeasyrequestreference(String teasyrequestreference) {
		this.teasyrequestreference = "201407010000001";
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankSortCode() {
		return bankSortCode;
	}

	public void setBankSortCode(String bankSortCode) {
		this.bankSortCode = bankSortCode;
	}

	public String getAgentMSISDN() {
		return agentMSISDN;
	}

	public void setAgentMSISDN(String agentMSISDN) {
		this.agentMSISDN = agentMSISDN;
	}

	public String getSubscriberMSISDN() {
		return subscriberMSISDN;
	}

	public void setSubscriberMSISDN(String subscriberMSISDN) {
		this.subscriberMSISDN = subscriberMSISDN;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	public String getSamplePin() {
		return samplePin;
	}

	public void setSamplePin(String samplePin) {
		this.samplePin = samplePin;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Sender: " + sender + "\n");
		sb.append("Receiver: " + receiver + "\n");
		sb.append("Amount: " + amount + "\n");
		sb.append("Reference: " + reference + "\n");
		sb.append("Mmo: " + mmo + "\n");
		// sb.append("Status: " + status + "\n");

		return sb.toString();
	}

}

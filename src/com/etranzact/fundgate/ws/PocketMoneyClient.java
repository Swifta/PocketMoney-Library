package com.etranzact.fundgate.ws;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang.RandomStringUtils;

import com.etranzact.fundgate.ws.FundGateImplServiceStub.FundRequest;
import com.etranzact.fundgate.ws.FundGateImplServiceStub.FundResponse;
import com.etranzact.fundgate.ws.FundGateImplServiceStub.Transaction;
import com.ng.mats.psa.mt.pocketmoni.model.MoneyTransfer;

public class PocketMoneyClient {

	private FundGateImplServiceStub fundgateStub = null;
	private static MoneyTransfer moneyTransfer = null;

	// EndpointReference fundgateURl = null;

	// String fundgateURl = "";

	// private String wso2appserverHome = "";

	// private final String agentMSISDN = "2347031537019";
	// private final static String subscriberMSISDN = "2348076763191";
	// private final static String agentMSISDN = "2348056892033";

	// private final String terminalId = "20000000054";

	// test parameters
	// private final String terminalId = "7000000001";
	// private final String masterKey = "KEd4gDNSDdMBxCGliZaC8w==";
	// private final String samplePin = "0012";

	// production parameters
	// private final static String agentMSISDN = "2348092847295";
	// private final static String subscriberMSISDN = "2348124442975";
	// private final String terminalId = "0300010001";
	// private final String masterKey = "R90aaowC0PrB2zILxzV1uw==";
	// private final String samplePin = "6219";

	public PocketMoneyClient() throws Exception {
		moneyTransfer = new PocketMoniPropertyValues().getPropertyValues();
		setMoneyTransfer(moneyTransfer);
		fundgateStub = new FundGateImplServiceStub();

		// if (System.getProperty("os.name").equals("Mac OS X")) {
		// wso2appserverHome = "/Users/user/Documents/workspace/wso2esb-4.8.1";
		// } else {
		// wso2appserverHome = "/opt/mats/wso2esb-4.8.1";
		// }
	}

	@SuppressWarnings("deprecation")
	public void configureSecurity(String clientSSLStore,
			String clientSSLPassword) throws UnknownHostException, IOException {
		// String clientSSLStore = wso2appserverHome + File.separator
		// + "repository" + File.separator + "resources" + File.separator
		// + "security" + File.separator + "client-truststore.jks";

		// wso2carbon.jks client-truststore.jks

		System.getProperties().remove("javax.net.ssl.trustStore");
		System.getProperties().remove("javax.net.ssl.trustStoreType");
		System.getProperties().remove("javax.net.ssl.trustStorePassword");

		System.setProperty("javax.net.ssl.trustStore", clientSSLStore);
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		System.setProperty("javax.net.ssl.trustStorePassword",
				clientSSLPassword);
		System.setProperty("jsse.enableSNIExtension", "false");
		// System.setProperty("javax.net.debug", "ssl");
		// System.setProperty("https.protocols", "SSLv3");
		// System.setProperty("https.protocols", "TLSV");
		java.lang.System.setProperty("jdk.tls.client.protocols",
				"TLSv1,TLSv1.1,TLSv1.2");
		Protocol myProtocolHandler = new Protocol("https",
				new TLSVProtocolSocketFactory(), 443);

		fundgateStub
				._getServiceClient()
				.getOptions()
				.setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER,
						myProtocolHandler);

		// fundgateStub._getServiceClient().getOptions()
		// .setProperty(HTTPConstants.CHUNKED, "false");

	}

	public FundResponse doCashIn(MoneyTransfer moneyTransfer) throws Exception {
		System.out.println("...Creating account...");
		System.out.println("---------------------------do cash in");
		if (fundgateStub == null) {
			System.out
					.println("------------------------Fund stub is not available");
		} else {
			System.out
					.println("---------------------------Fund Gate stub is available");

		}

		FundRequest fundRequest = new FundRequest();
		fundRequest.setDirection("request");
		fundRequest.setAction("FT");
		fundRequest.setId("1");
		fundRequest.setTerminalId(moneyTransfer.getTerminalId());

		Transaction transaction = new Transaction();
		transaction.setPin(AES.encrypt(moneyTransfer.getSamplePin(),
				moneyTransfer.getMasterKey()));

		transaction.setAmount(moneyTransfer.getAmount());
		transaction.setDestination(moneyTransfer.getReceiver());// 2348076763191
		transaction.setSource(moneyTransfer.getSender());
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("YYYYMMddHHmmSSS");
		transaction.setReference(ft.format(dNow));
		transaction.setEndPoint("M");
		transaction.setId("1274466360545600");

		System.out.println("==============================="
				+ transaction.toString());
		fundRequest.setTransaction(transaction);

		com.etranzact.fundgate.ws.FundGateImplServiceStub.Process process = new com.etranzact.fundgate.ws.FundGateImplServiceStub.Process();
		process.setRequest(fundRequest);

		com.etranzact.fundgate.ws.FundGateImplServiceStub.ProcessResponse processResponse = fundgateStub
				.process(process);

		return processResponse.getResponse();

	}

	public FundResponse accountQuery(MoneyTransfer moneyTransfer)
			throws Exception {
		System.out.println("...Account query...");
		System.out.println("---------------------------do query");
		if (fundgateStub == null) {
			System.out
					.println("------------------------Fund stub is not available");
		} else {
			System.out
					.println("---------------------------Fund Gate stub is available");

		}
		FundRequest fundRequest = new FundRequest();
		fundRequest.setDirection("request");
		fundRequest.setAction("AQ");
		fundRequest.setId("1");
		fundRequest.setTerminalId(moneyTransfer.getTerminalId());

		Transaction transaction = new Transaction();
		transaction.setDestination(moneyTransfer.getBankAccountNumber());
		transaction.setEndPoint("M");
		transaction.setBankCode(moneyTransfer.getBankSortCode());

		transaction.setPin(AES.encrypt(moneyTransfer.getSamplePin(),
				moneyTransfer.getMasterKey()));

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("YYYYMMddHHmmSSS");
		transaction.setReference(ft.format(dNow));

		transaction.setId("1274466360545600");

		System.out.println("==============================="
				+ transaction.toString());

		fundRequest.setTransaction(transaction);

		com.etranzact.fundgate.ws.FundGateImplServiceStub.Process process = new com.etranzact.fundgate.ws.FundGateImplServiceStub.Process();
		process.setRequest(fundRequest);

		com.etranzact.fundgate.ws.FundGateImplServiceStub.ProcessResponse processResponse = fundgateStub
				.process(process);

		return processResponse.getResponse();
	}

	public FundResponse doCashOut(MoneyTransfer moneyTransfer) throws Exception {
		System.out.println("...Creating account...");
		System.out.println("---------------------------do cash out");
		if (fundgateStub == null) {
			System.out
					.println("------------------------Fund stub is not available");
		} else {
			System.out
					.println("---------------------------Fund Gate stub is available");

		}

		FundRequest fundRequest = new FundRequest();
		fundRequest.setDirection("request");
		fundRequest.setAction("FT");
		fundRequest.setId("1");
		fundRequest.setTerminalId(moneyTransfer.getTerminalId());

		Transaction transaction = new Transaction();
		transaction.setPin(AES.encrypt(moneyTransfer.getSamplePin(),
				moneyTransfer.getMasterKey()));

		transaction.setAmount(moneyTransfer.getAmount());
		transaction.setDestination(moneyTransfer.getReceiver());// 2348076763191
		transaction.setSource(moneyTransfer.getSender());
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("YYYYMMddHHmmSSS");
		transaction.setReference(ft.format(dNow));
		transaction.setEndPoint("M");
		transaction.setId("1274466360545600");

		System.out.println("==============================="
				+ transaction.toString());

		fundRequest.setTransaction(transaction);
		com.etranzact.fundgate.ws.FundGateImplServiceStub.Process process = new com.etranzact.fundgate.ws.FundGateImplServiceStub.Process();
		process.setRequest(fundRequest);

		com.etranzact.fundgate.ws.FundGateImplServiceStub.ProcessResponse processResponse = fundgateStub
				.process(process);

		return processResponse.getResponse();

	}

	public FundResponse getBalance(MoneyTransfer moneyTransfer)
			throws Exception {

		System.out.println("...Creating account...");
		System.out.println("---------------------------do get balance");
		if (fundgateStub == null) {
			System.out
					.println("------------------------Fund stub is not available");
		} else {
			System.out
					.println("---------------------------Fund Gate stub is available");

		}

		FundRequest fundRequest = new FundRequest();
		fundRequest.setDirection("request");
		fundRequest.setAction("BE");
		fundRequest.setId("1");
		fundRequest.setTerminalId(moneyTransfer.getTerminalId());

		Transaction transaction = new Transaction();
		transaction.setPin(AES.encrypt(moneyTransfer.getSamplePin(),
				moneyTransfer.getMasterKey()));

		// transaction.setAmount(moneyTransfer.getAmount());
		// transaction.setDestination(moneyTransfer.getReceiver());//
		// 2348076763191
		// transaction.setSource(moneyTransfer.getSender());
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("YYYYMMddHHmmSSS");
		transaction.setReference(ft.format(dNow));
		// transaction.setEndPoint("M");
		transaction.setId("1274466360545600");

		System.out.println("==============================="
				+ transaction.toString());

		fundRequest.setTransaction(transaction);

		/*
		 * FundRequest fundRequest = new FundRequest();
		 * fundRequest.setDirection("request"); fundRequest.setAction("BE");
		 * fundRequest.setId("1"); fundRequest.setTerminalId(terminalId);
		 * 
		 * Transaction transaction = new Transaction();
		 * transaction.setPin(AES.encrypt(samplePin, masterKey));x //
		 * transaction.setPin(AES.encrypt("0012", "ZhXy4geRgnpqVOH/7V2beg=="));
		 * // transaction.setPin("ZhXy4geRgnpqVOH/7V2beg=="); //
		 * transaction.setTerminalCard(false); //
		 * transaction.setBankCode("033"); //
		 * transaction.setDestination(moneyTransfer.getReceiver());// //
		 * 2348076763191 // transaction.setSource(moneyTransfer.getSender()); //
		 * transaction.setAmount(0.0); // transaction.setEndPoint("0"); //
		 * transaction.setIdentification(moneyTransfer.getReceiver());
		 * 
		 * // transaction.setTerminalCard(false); Date dNow = new Date();
		 * SimpleDateFormat ft = new SimpleDateFormat("YYYYMMddHHmmSSS");
		 * transaction.setReference(ft.format(dNow));
		 * 
		 * fundRequest.setTransaction(transaction);
		 */

		com.etranzact.fundgate.ws.FundGateImplServiceStub.Process process = new com.etranzact.fundgate.ws.FundGateImplServiceStub.Process();
		process.setRequest(fundRequest);

		com.etranzact.fundgate.ws.FundGateImplServiceStub.ProcessResponse processResponse = fundgateStub
				.process(process);

		return processResponse.getResponse();

	}

	public FundResponse sellAirtime(MoneyTransfer moneyTransfer)
			throws Exception {
		System.out.println("...Creating account...");
		System.out.println("---------------------------do cash in");
		if (fundgateStub == null) {
			System.out
					.println("------------------------Fund stub is not available");
		} else {
			System.out
					.println("---------------------------Fund Gate stub is available");

		}

		FundResponse fundResponse = new FundResponse();
		return fundResponse;

	}

	public FundResponse payBill(MoneyTransfer moneyTransfer) throws Exception {
		System.out.println("...Creating account...");
		System.out.println("---------------------------do cash in");
		if (fundgateStub == null) {
			System.out
					.println("------------------------Fund stub is not available");
		} else {
			System.out
					.println("---------------------------Fund Gate stub is available");

		}

		FundResponse fundResponse = new FundResponse();
		return fundResponse;

	}

	public static void main(String args[]) throws Exception {

		// performCashInPocket();
		// performCashoutPocket();
		performBalanceCheck();
		// performAccountQuery();

	}

	public static void performAccountQuery() throws Exception {

		// MoneyTransfer moneyTransfer = new MoneyTransfer("", "2348092847295",
		// null, 5, null, "dada", "6219");

		PocketMoneyClient pocketMoneyClient = new PocketMoneyClient();
		System.out
				.println("---------------------------Before configuring security**********");
		pocketMoneyClient.configureSecurity(
				moneyTransfer.getTrustStoreLocation(),
				moneyTransfer.getTrustStorePassword());
		;
		System.out
				.println("---------------------------After configuring security**********");
		FundResponse response = pocketMoneyClient.accountQuery(moneyTransfer);

		System.out.println("Error Code: " + response.getError());
		System.out.println("Message: " + response.getMessage());
		System.out.println("Other reference: " + response.getOtherReference());
		System.out.println("Reference: " + response.getReference());
		System.out.println("Amount: " + response.getAmount());
		System.out.println("Date: " + response.getDate());
	}

	public static void performCashoutPocket() throws Exception {
		// MoneyTransfer moneyTransfer = new MoneyTransfer("2348170730603",
		// "2348056892033", null, 3, null, "dada", "7005");

		PocketMoneyClient pocketMoneyClient = new PocketMoneyClient();
		moneyTransfer.setSender(moneyTransfer.getSubscriberMSISDN());
		moneyTransfer.setReceiver(moneyTransfer.getAgentMSISDN());

		System.out
				.println("---------------------------Before configuring security**********");
		pocketMoneyClient.configureSecurity(
				moneyTransfer.getTrustStoreLocation(),
				moneyTransfer.getTrustStorePassword());
		;
		System.out
				.println("---------------------------After configuring security**********");

		FundResponse response = pocketMoneyClient.doCashOut(moneyTransfer);

		System.out.println("Error Code: " + response.getError());
		System.out.println("Message: " + response.getMessage());
		System.out.println("Other reference: " + response.getOtherReference());
		System.out.println("Reference: " + response.getReference());
		System.out.println("Amount: " + response.getAmount());
		System.out.println("Date: " + response.getDate());
	}

	public static void performCashInPocket() throws Exception {
		// MoneyTransfer moneyTransfer = new MoneyTransfer("", "2348076763199",
		// null, 3, null, "dada", "7005");

		PocketMoneyClient pocketMoneyClient = new PocketMoneyClient();
		moneyTransfer.setReceiver(moneyTransfer.getSubscriberMSISDN());
		moneyTransfer.setSender(moneyTransfer.getAgentMSISDN());
		System.out
				.println("---------------------------Before configuring security**********");
		pocketMoneyClient.configureSecurity(
				moneyTransfer.getTrustStoreLocation(),
				moneyTransfer.getTrustStorePassword());
		;
		System.out
				.println("---------------------------After configuring security**********");
		FundResponse response = pocketMoneyClient.doCashIn(moneyTransfer);

		System.out.println("Error Code: " + response.getError());
		System.out.println("Message: " + response.getMessage());
		System.out.println("Other reference: " + response.getOtherReference());
		System.out.println("Reference: " + response.getReference());
		System.out.println("Amount: " + response.getAmount());
		System.out.println("Date: " + response.getDate());

	}

	public static void performBalanceCheck() throws Exception {
		// MoneyTransfer moneyTransfer = new MoneyTransfer("", "2348092847295",
		// null, 5, null, "dada", "6219");

		PocketMoneyClient pocketMoneyClient = new PocketMoneyClient();
		System.out
				.println("---------------------------Before configuring security**********");
		pocketMoneyClient.configureSecurity(
				moneyTransfer.getTrustStoreLocation(),
				moneyTransfer.getTrustStorePassword());
		;
		System.out
				.println("---------------------------After configuring security**********");
		FundResponse response = pocketMoneyClient.getBalance(moneyTransfer);

		System.out.println("Error Code: " + response.getError());
		System.out.println("Message: " + response.getMessage());
		System.out.println("Other reference: " + response.getOtherReference());
		System.out.println("Reference: " + response.getReference());
		System.out.println("Amount: " + response.getAmount());
		System.out.println("Date: " + response.getDate());

	}

	public static String generateReferencenNumber(int length) {
		// random PIN number
		String transactionId = "";
		transactionId = RandomStringUtils.randomNumeric(length);
		return transactionId;
	}

	public static MoneyTransfer getMoneyTransfer() {
		return moneyTransfer;
	}

	public static void setMoneyTransfer(MoneyTransfer moneyTransfer) {
		PocketMoneyClient.moneyTransfer = moneyTransfer;
	}

}

package com.etranzact.fundgate.ws;

import java.io.File;
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

	// EndpointReference fundgateURl = null;

	// String fundgateURl = "";

	private String wso2appserverHome = "";
	private final String agentMSISDN = "2347031537019";
	private final String subscriberMSISDN = "2348076763191";
	private final String terminalId = "20000000054";
	private final String masterKey = "KEd4gDNSDdMBxCGliZaC8w==";
	private final String samplePin = "0012";

	public PocketMoneyClient() throws Exception {

		fundgateStub = new FundGateImplServiceStub();

		if (System.getProperty("os.name").equals("Mac OS X")) {
			wso2appserverHome = "/Users/user/Documents/workspace/wso2esb-4.8.1";
		} else {
			wso2appserverHome = "/opt/mats/wso2esb-4.8.1";
		}
	}

	@SuppressWarnings("deprecation")
	public void configureSecurity() throws UnknownHostException, IOException {
		String clientSSLStore = wso2appserverHome + File.separator
				+ "repository" + File.separator + "resources" + File.separator
				+ "security" + File.separator + "client-truststore.jks";

		// wso2carbon.jks client-truststore.jks

		System.getProperties().remove("javax.net.ssl.trustStore");
		System.getProperties().remove("javax.net.ssl.trustStoreType");
		System.getProperties().remove("javax.net.ssl.trustStorePassword");

		System.setProperty("javax.net.ssl.trustStore", clientSSLStore);
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
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

		moneyTransfer.setReceiver(subscriberMSISDN);
		moneyTransfer.setSender(agentMSISDN);

		// FundRequest fundRequest = new FundRequest();
		// fundRequest.setDirection("request");
		// fundRequest.setAction("FT");
		// fundRequest.setId("1");
		// fundRequest.setTerminalId("20000000054");
		//
		// Transaction transaction = new Transaction();
		// transaction.setPin(AES.encrypt("0012", "KEd4gDNSDdMBxCGliZaC8w=="));
		// transaction.setBankCode("033");
		// transaction.setAmount(10.0);
		// transaction.setDestination("2001220212");
		// transaction.setReference("4402333999223");
		// transaction.setEndPoint("A");
		// transaction.setId("1274466360545600");
		//
		FundRequest fundRequest = new FundRequest();
		fundRequest.setDirection("request");
		fundRequest.setAction("FT");
		fundRequest.setId("1");
		fundRequest.setTerminalId(terminalId);

		Transaction transaction = new Transaction();
		transaction.setPin(AES.encrypt(samplePin, masterKey));
		// transaction.setToken("TA");
		// transaction.setTerminalCard(false);
		// transaction.setBankCode("033");
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

		moneyTransfer.setSender(subscriberMSISDN);
		moneyTransfer.setReceiver(agentMSISDN);

		FundRequest fundRequest = new FundRequest();
		fundRequest.setDirection("request");
		fundRequest.setAction("FT");
		// fundRequest.setId("1");
		fundRequest.setTerminalId(terminalId);

		Transaction transaction = new Transaction();
		transaction.setPin(AES.encrypt(samplePin, masterKey));
		// transaction.setTerminalCard(false);
		// transaction.setBankCode("033");
		transaction.setAmount(moneyTransfer.getAmount());
		// transaction.setToken("TA");
		transaction.setDestination(moneyTransfer.getReceiver());// 2348076763191
		transaction.setSource(moneyTransfer.getSender());
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("YYYYMMddHHmmSSS");
		transaction.setReference(ft.format(dNow));
		transaction.setEndPoint("M");
		transaction.setId("1274466360545600");
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
		fundRequest.setAction("BE");
		fundRequest.setId("1");
		fundRequest.setTerminalId("20000000054");

		Transaction transaction = new Transaction();
		transaction.setPin(AES.encrypt("0012", "KEd4gDNSDdMBxCGliZaC8w=="));
		// transaction.setTerminalCard(false);
		// transaction.setBankCode("033");
		transaction.setAmount(0.0);
		transaction.setEndPoint("0");
		transaction.setTerminalCard(false);
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("YYYYMMddHHmmSSS");
		transaction.setReference(ft.format(dNow));

		fundRequest.setTransaction(transaction);

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

	// public static String Encrypt(String plainText, String key)
	// throws NoSuchAlgorithmException, NoSuchPaddingException,
	// InvalidKeyException, IllegalBlockSizeException,
	// BadPaddingException, UnsupportedEncodingException {
	//
	// //String cleartext = "test1234test1234";
	// //String key = "TESTKEYTESTKEY12";
	// byte[] raw = key.getBytes();
	// SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	// try {
	// Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	// cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	// byte[] encrypted;
	// encrypted = cipher.doFinal(plainText.getBytes());
	// return new String(Base64.encodeBase64(encrypted));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;

	// SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	//
	// // Instantiate the cipher
	// Cipher cipher = Cipher.getInstance("AES");
	// cipher.init(Cipher.ENCRYPT_MODE, keySpec);
	//
	// byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
	//
	// return new Base64().encodeToString(encryptedTextBytes);

	public static void main(String args[]) throws Exception {

		performCashInPocket();
		// performCashoutPocket();

	}

	public static void performCashoutPocket() throws Exception {
		MoneyTransfer moneyTransfer = new MoneyTransfer("", "2348076763191",
				null, 100, null, "dada", "7005");

		PocketMoneyClient pocketMoneyClient = new PocketMoneyClient();

		pocketMoneyClient.configureSecurity();

		FundResponse response = pocketMoneyClient.doCashOut(moneyTransfer);

		System.out.println("Error Code: " + response.getError());
		System.out.println("Message: " + response.getMessage());
		System.out.println("Other reference: " + response.getOtherReference());
		System.out.println("Reference: " + response.getReference());
		System.out.println("Amount: " + response.getAmount());
		System.out.println("Date: " + response.getDate());
	}

	public static void performCashInPocket() throws Exception {
		MoneyTransfer moneyTransfer = new MoneyTransfer("", "2348076763191",
				null, 100, null, "dada", "7005");

		PocketMoneyClient pocketMoneyClient = new PocketMoneyClient();
		System.out
				.println("---------------------------Before configuring security**********");
		pocketMoneyClient.configureSecurity();
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

	public static String generateReferencenNumber(int length) {
		// random PIN number
		String transactionId = "";
		transactionId = RandomStringUtils.randomNumeric(length);
		return transactionId;
	}

}

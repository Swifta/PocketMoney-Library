package com.etranzact.fundgate.ws;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import com.ng.mats.psa.mt.pocketmoni.model.MoneyTransfer;

public class PocketMoniPropertyValues {
	private static final Logger logger = Logger
			.getLogger(PocketMoniPropertyValues.class.getName());

	public MoneyTransfer getPropertyValues() {

		MoneyTransfer moneyTransfer = new MoneyTransfer();
		Properties prop = new Properties();
		String propFileName = "com/etranzact/fundgate/ws/config.properties";

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propFileName);
		try {
			if (inputStream != null) {

				prop.load(inputStream);

			} else {
				throw new FileNotFoundException("property file '"
						+ propFileName + "' not found in the classpath");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// get the property value and print it out
		String parameterType = prop.getProperty("settings-type");
		logger.info("THE CONFIGURATION BEING USED AT THIS POINT IS ==========================="
				+ parameterType);

		moneyTransfer.setAmount(Long.valueOf(prop.getProperty("Amount_"
				+ parameterType)));
		moneyTransfer.setAgentMSISDN(prop.getProperty("AgentMSISDN_"
				+ parameterType));
		moneyTransfer.setBankAccountNumber(prop
				.getProperty("BankAccountNumber_" + parameterType));
		moneyTransfer.setBankSortCode(prop.getProperty("BankSortCode_"
				+ parameterType));
		moneyTransfer.setMasterKey(prop.getProperty("MasterKey_"
				+ parameterType));
		moneyTransfer.setMmo(prop.getProperty("Mmo_" + parameterType));
		moneyTransfer.setReference(prop.getProperty("Reference_"
				+ parameterType));
		moneyTransfer.setSamplePin(prop.getProperty("SamplePin_"
				+ parameterType));
		moneyTransfer.setSubscriberMSISDN(prop.getProperty("SubscriberMSISDN_"
				+ parameterType));
		moneyTransfer.setTerminalId(prop.getProperty("TerminalId_"
				+ parameterType));

		moneyTransfer
				.setTeasypin(prop.getProperty("Teasypin_" + parameterType));
		moneyTransfer
				.setReceiver(prop.getProperty("Reciever_" + parameterType));
		return moneyTransfer;

	}
}
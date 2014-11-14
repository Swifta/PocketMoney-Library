package com.etranzact.fundgate.ws;

import java.util.concurrent.atomic.AtomicLong;

import com.etranzact.fundgate.ws.FundGateImplServiceStub.FundResponse;
import com.ng.mats.psa.mt.pocketmoni.model.MoneyTransfer;

public class Client {

	private static final AtomicLong LAST_TIME_MS = new AtomicLong();

	public static long uniqueCurrentTimeMS() {
		long now = System.currentTimeMillis();
		while (true) {
			long lastTime = LAST_TIME_MS.get();
			if (lastTime >= now)
				now = lastTime + 1;
			if (LAST_TIME_MS.compareAndSet(lastTime, now))
				return now;
		}
	}

	public static long unique() {
		long now = System.currentTimeMillis();
		return now;
	}

	public static void main(String args[]) throws Exception {

		System.out.println("Get Message: " + Client.uniqueCurrentTimeMS());

		System.out.println("Get Message: " + Client.unique());

		System.out.println(System.getProperty("os.name"));

		PocketMoneyClient pocketMoneyClient = new PocketMoneyClient();

		pocketMoneyClient.configureSecurity();
		String sender = "2348063305711", reciever = "2348076763191", transactionType = "cashin", mmo = "pocketmoni", reference = "1234", teasypin = "1234";
		long amount = 200;
		MoneyTransfer moneyTransfer = new MoneyTransfer(sender, reciever,
				transactionType, amount, mmo, reference, teasypin);
		FundResponse response = pocketMoneyClient.doCashIn(moneyTransfer);
		System.out.println("Get Message: " + response.getMessage());

	}

}

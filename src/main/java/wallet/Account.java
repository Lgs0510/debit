/**
 * 
 */
package wallet;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import org.apache.logging.log4j.*;
/*
 * 
 */
public class Account extends Money {
	//Static variables
	private static ArrayList<Boolean> idList = new ArrayList<Boolean>();
	private static int biggestId=0;
	private static Logger AccountLogger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	// Non-Static variables
	private Money balance;
	protected int id;
	
	/**
	 * Class constructor specifying the currency type
	 * @param currency
	 */
	public Account(Currency_t currency) {
		super(0, currency);
		this.balance=new Money(0, currency);
		this.id=idDealer();
		AccountLogger.info("A new account, id: " + this.id + " was created!");
	}
	
	/**
	 * Class constructor specifying the currency type and the starting value
	 * @param currency
	 * @param startBalance
	 */
	public Account(Money startBalance) {
		super(0, startBalance.getCurrency());
		this.balance=new Money(startBalance.getAmount(), startBalance.getCurrency());
		this.id=idDealer();
		AccountLogger.info("A new account, id: " + this.id + " was created!");
	}

	/**
	 * Free account ID 
	 */
	public void ClosseAccount() {
		idList.set(this.id, Boolean.FALSE);
	}
	/**
	 * allocate and return a new/available ID number
	 * @return
	 */
	private static int idDealer() {
		int emptyIndex=0;
		int curId=0;

		if(idList.contains(Boolean.FALSE)) {
			emptyIndex = idList.indexOf(Boolean.FALSE);
			idList.set(emptyIndex, Boolean.TRUE);
			curId=emptyIndex;
		}
		else {
			idList.add(Boolean.TRUE);
			biggestId=idList.lastIndexOf(Boolean.TRUE);
			curId=biggestId;
		}
		return curId;
	}
	
	
	@Override
	public double getAmount() {return this.balance.getAmount();}
	public int getAccID() {return this.id;}

	/**
	 * Add funds' amount of money to the current account IF currency match and "funds" is greater than zero
	 * @param funds
	 */
	public void addFunds(Money funds) {
		
		if (funds.getAmount() < 0) {
			AccountLogger.error("It is not possible to add negative money!");
			throw new IllegalArgumentException("It is not possible to add negative money!");
		}
		
		if(!this.balance.currencyMatch(funds.getCurrency())) {
			AccountLogger.error("It is not possible to add money from other currencies right now!");
			throw new IllegalArgumentException("It is not possible to add money from other currencies right now!");
		}
			
		if(this.getAmount() + funds.getAmount() > 3000000000.00) {
			AccountLogger.error("Amount of money in the account cannot be bigger then 4 billion($4000000000.00)");
			throw new IllegalArgumentException("Amount of money in the account cannot be bigger then 4 billion($4000000000.00)");
		}
		
		this.balance.setAmount(this.balance.getAmount() + funds.getAmount());
		AccountLogger.debug("A total amount of " + funds.getAmount() + " was added sucessfuly!");
	}
	
	/**
	 * Withdraw money from the current account if there is enough for it and the currency matches
	 * @param desiredFunds
	 */
	public void withdrawFunds(Money desiredFunds) {
		if(desiredFunds.getAmount() < 0) {
			AccountLogger.error("It is not possible to withdraw negative money!");
			throw new IllegalArgumentException("It is not possible to withdraw negative money!");
		}
		
		if (desiredFunds.getAmount() > this.balance.getAmount()) {
			AccountLogger.error("It is not possible to withdraw more money than what is available!");
			throw new IllegalArgumentException("It is not possible to withdraw more money than what is available!");
		}
		
		if(!this.balance.currencyMatch(desiredFunds.getCurrency())) {
			AccountLogger.error("It is not possible to retrieve money from other currencies right now!");
			throw new IllegalArgumentException("It is not possible to retrieve money from other currencies right now!");
		}
			
		this.balance.setAmount(this.balance.getAmount() - desiredFunds.getAmount());
		AccountLogger.debug("A total amount of " + desiredFunds.getAmount() + " was withdrawn sucessfuly!");
	}
}

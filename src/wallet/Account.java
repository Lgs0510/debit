package wallet;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.*;

/**
 * Account class implements a simple account type, that has money and methods to get, add and withdraw funds
 */
public class Account extends Money {
	private Money balance;
	private Currency_t currency=Currency_t.BRL;
	private static Logger AccountLogger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	/**
	 * Class constructor specifying the currency type
	 * @param currency
	 */
	public Account(Currency_t currency) {
		super(0, currency);
		this.balance=new Money(0, currency);
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
	}
	
	@Override
	public double getAmount() {return this.balance.getAmount();}

	/**
	 * Add funds' amount of money to the current account IF currency match and "funds" is greater than zero
	 * @param funds
	 */
	public void addFunds(Money funds) {
		if (funds.getAmount() > 0) {
			if(this.balance.currencyMatch(funds.getCurrency())) {
				this.balance.setAmount(this.balance.getAmount() + funds.getAmount());
			}
			else {
				AccountLogger.error("It is not possible to add money from other currencies right now!");
				throw new IllegalArgumentException("It is not possible to add money from other currencies right now!");
			}
		}
		else {
			AccountLogger.error("It is not possible to add negative money!");
			throw new IllegalArgumentException("It is not possible to add negative money!");
		}

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
			AccountLogger.error("It is not possible to add money from other currencies right now!");
			throw new IllegalArgumentException("It is not possible to add money from other currencies right now!");
		}
			
		this.balance.setAmount(this.balance.getAmount() - desiredFunds.getAmount());
		AccountLogger.debug("A total amount of " + desiredFunds.getAmount() + " was withdrawn sucessfuly!");
	}
}

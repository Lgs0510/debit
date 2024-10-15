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
		balance=new Money(0, currency);
		AccountLogger.info("A new account was created!");
	}
	
	public Account(Currency_t currency, Money startBalance) {
		super(0, currency);
		balance=new Money(startBalance.getAmount(), currency);
	}
	
	@Override
	public double getAmount() {return this.balance.getAmount();}

	/**
	 * Add funds' amount of money to the current account IF currency match and "funds" is greater than zero
	 * @param funds
	 */
	public void addFunds(Money funds) {
		if (funds.getAmount() > 0) {
			if(balance.currencyMatch(funds.getCurrency())) {
				balance.setAmount(balance.getAmount() + funds.getAmount());
			}
			else {
				throw new IllegalArgumentException("It is not possible to add money from other currencies right now!");
			}
		}
		else {
			throw new IllegalArgumentException("It is not possible to add negative money!");
		}
	}
	
	/**
	 * Withdraw money from the current account if there is enough for it and the currency matches
	 * @param desiredFunds
	 */
	public void withdrawFunds(Money desiredFunds) {
		if(desiredFunds.getAmount() < 0) {
			throw new IllegalArgumentException("It is not possible to withdraw negative money!");
		}
		
		if (desiredFunds.getAmount() > balance.getAmount()) {
			throw new IllegalArgumentException("It is not possible to withdraw more money than what is available!");
		}
		
		if(balance.currencyMatch(desiredFunds.getCurrency())) {
			throw new IllegalArgumentException("It is not possible to add money from other currencies right now!");
		}
			
		balance.setAmount(balance.getAmount() - desiredFunds.getAmount());
		
	}
}

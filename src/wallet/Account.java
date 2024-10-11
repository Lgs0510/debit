package wallet;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.*;

public class Account extends Money {
	private Money balance;
	private Currency_t currency=Currency_t.BRL;
	private static Logger AccountLogger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
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

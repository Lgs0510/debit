package wallet;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankAccount extends Account {
	private static Logger bankAccLogger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	public BankAccount() {
		super(Currency_t.BRL);
		
		bankAccLogger.info("A new bank account was created!");
	}

}

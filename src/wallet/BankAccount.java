package wallet;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankAccount extends Account {
	private static Logger bankAccLogger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private static HashMap<String, String> bankCodesMap = new HashMap<>();
	private String bankName;
	private String accountNumber;
	private String accountAgency;

	/**
	 * Class Constructor
	 */
	public BankAccount(String bankName, String bankID, String accountNumber, String accountAgency) {
		super(Currency_t.BRL);
		
		if(!checkBankIdVsName(bankName, bankID)){
			bankAccLogger.error("The bank name does not match the bank ID");
			throw new IllegalArgumentException("The bank name does not match the bank ID");
		}
		bankAccLogger.info("A new bank account was created!");
		this.bankName = bankName;
		this.accountNumber =accountNumber;
		this.accountAgency =accountAgency;
	}

	/**
	 * Checks the bank code with its name from a internal list of banks and their respective code
	 * @param bankName - String class
	 * @param bankId - String class
	 * @return - boolean with true if the bankName and bankID are valid
	 */
	private static boolean checkBankIdVsName(String bankName, String bankId){
		
		if(bankCodesMap.isEmpty()) {
			getBankCodes();
			if(bankCodesMap.isEmpty()) {
				bankAccLogger.error("Cannot check the bank ID!");
				throw new NullPointerException("Bank Codes map is empty!");
			}
			if(bankCodesMap.containsKey(bankId)){
				if(bankName.compareTo(bankCodesMap.get(bankId))==0){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Open and read the BrazilBanksCodes.csv file to assembly a map with the banks' names and their code
	 * @return
	 */
	private static boolean getBankCodes() {
		File listBanksCodes;
		List<String> banksCodes;
		
		listBanksCodes=new File("./Src/Resources/BrazilBanksCodes.csv");
		if(listBanksCodes.canRead()){
			try {
				banksCodes=(Files.readAllLines(listBanksCodes.toPath()));
			} catch (IOException e) {
				bankAccLogger.error("Fail to oppen BrazilBanksCodes.csv");
				e.printStackTrace();
				return false;
			}
			for(String line : banksCodes) {
				bankCodesMap.put(line.split(";")[0], line.split(";")[1]);
			}
		}
		else {
			bankAccLogger.error("BrazilBanksCodes.csv has no read permission!");
			return false;
		}
		return true;
	}
	
	public String getAccNumber() { return this.accountNumber;}
	
	public String getAccAgency() {return this.accountAgency;}
	
	public String getBankName() {return this.bankName;}
}

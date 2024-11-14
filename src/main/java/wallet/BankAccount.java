/**
 * 
 */
package wallet;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 
 */
public class BankAccount extends Account {
	private static Logger bankAccLogger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private static HashMap<String, String> bankCodesMap = new HashMap<>();
	private String bankName;
	private String accountNumber;
	private String accountAgency;
	/**
	 * @param currency
	 */
	public BankAccount(String bankName, String bankID, String accountNumber, String accountAgency) {
		super(Currency_t.BRL);
		
		if(!checkBankIdVsName(bankName, bankID)){
			bankAccLogger.error("The bank name does not match the bank ID");
			throw new IllegalArgumentException("The bank name does not match the bank ID");
		}
		if((accountNumber == null) || (accountNumber == "")){
			bankAccLogger.error("The account number MUST be a valid number");
			throw new IllegalArgumentException("The account number MUST be a valid number");
		}

		if((accountAgency == null)||(accountAgency == "")){
			bankAccLogger.error("The agency number MUST be a valid number");
			throw new IllegalArgumentException("The agency number MUST be a valid number");
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
		Integer bnkIdNb;
		
		if(bankCodesMap.isEmpty()) {
			getBankCodes();
			if(bankCodesMap.isEmpty()) {
				bankAccLogger.error("Bank Codes map is empty!");
				throw new NullPointerException("Bank Codes map is empty!");
			}
		}
		if(bankId == null){
			bankAccLogger.error("The bank ID cannot be NULL");
			throw new InvalidParameterException("The bank ID cannot be NULL");
		}
		if(bankId == ""){
			bankAccLogger.error("The bank ID cannot be empty");
			throw new InvalidParameterException("The bank ID cannot be empty");
		}
		if (bankName==null) {
			bankAccLogger.error("The bank name cannot be NULL");
			throw new InvalidParameterException("The bank name cannot be NULL");
		}
		if(bankName == ""){
			bankAccLogger.error("The bank name cannot be empty");
			throw new InvalidParameterException("The bank name cannot be empty");
		}
		bnkIdNb=Integer.parseInt(bankId);
		if(bankCodesMap.containsKey(bnkIdNb.toString())){
			if(bankName.compareTo(bankCodesMap.get(bnkIdNb.toString()))==0){
				return true;
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
		
		listBanksCodes=new File("Resources/BrazilBanksCodes.csv");
		boolean testingFile=listBanksCodes.exists();
		testingFile=listBanksCodes.isFile();
		System.out.print(testingFile);
		listBanksCodes.setReadable(true);
		listBanksCodes=new File("Resources/BrazilBanksCodes.csv");
		if(!listBanksCodes.exists()) {
			return false;
		}
		try {
			listBanksCodes.createNewFile();
			listBanksCodes.setReadable(true);
			if(listBanksCodes.canRead()){
				banksCodes=(Files.readAllLines(listBanksCodes.toPath(), StandardCharsets.ISO_8859_1));
			}
			else {
				bankAccLogger.error("BrazilBanksCodes.csv has no read permission!");
				return false;
			}
		} catch (IOException e) {
			bankAccLogger.error("Fail to oppen BrazilBanksCodes.csv");
			e.printStackTrace();
			return false;
		}
		for(String line : banksCodes) {
			bankCodesMap.put(line.split(";")[0], line.split(";")[1]);
			bankAccLogger.debug(line);
		}
		return true;
	}
	
	public String getAccNumber() { return this.accountNumber;}
	
	public String getAccAgency() {return this.accountAgency;}
	
	public String getBankName() {return this.bankName;}
}

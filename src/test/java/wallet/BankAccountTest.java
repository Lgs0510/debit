/**
 * 
 */
package wallet;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 
 */
class BankAccountTest {

	/**
	 * Test method for {@link wallet.BankAccount#BankAccount(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	 @ParameterizedTest
	 @MethodSource("testBankAccountInputs")
	final void testBankAccount(String bkName, String bkId, String accNum, String accAgency, String exepMsg) {
		Exception exception = assertThrows(IllegalArgumentException.class, ()->{
			new BankAccount(bkName, bkId, accNum, accAgency);
		});
		 assertEquals(exepMsg, exception.getMessage());
	}
	 static Stream<Arguments> testBankAccountInputs(){
		 return Stream.of(
				 Arguments.arguments("Banco Santander (Brasil) S.A.", "044", "123", "0101", "The bank name does not match the bank ID"),
				 Arguments.arguments("Banco Cooperativo Sicredi S.A.", "", "99999","0101", "The bank ID cannot be empty"),
				 Arguments.arguments("Banco Bradesco S.A", null, "001100","0101", "The bank ID cannot be NULL"),
				 Arguments.arguments("Nubank", "260", "9999999","0101", "The bank name does not match the bank ID"),
				 Arguments.arguments("", "260", "9999999","0101", "The bank name cannot be empty"),
				 Arguments.arguments(null, "260", "9999999","0101", "The bank name cannot be NULL"),
				 Arguments.arguments("Banco Bradesco S.A.", "237", "", "0101", "The account number MUST be a valid number"),
				 Arguments.arguments("Banco Bradesco S.A.", "237", null, "0101", "The account number MUST be a valid number"),
				 Arguments.arguments("Banco Bradesco S.A.", "237", "12345", "", "The agency number MUST be a valid number"),
				 Arguments.arguments("Banco Bradesco S.A.", "237", "12345", null, "The agency number MUST be a valid number")
				 );
	 }

	/**
	 * Test method for {@link wallet.BankAccount#getAccNumber()}.
	 */
	 @ParameterizedTest
	 @MethodSource("testGetAccNumberInputs")
	final void testGetAccNumber(String bkName, String bkId, String accNum) {
		 BankAccount bankAcc=new BankAccount(bkName, bkId, accNum, "0101");
		 assertEquals(accNum, bankAcc.getAccNumber());
	}
	 static Stream<Arguments> testGetAccNumberInputs(){
		 return Stream.of(
				 Arguments.arguments("Banco Santander (Brasil) S.A.", "033", "123"),
				 Arguments.arguments("Banco Cooperativo Sicredi S.A.", "748", "99999"),
				 Arguments.arguments("Banco Bradesco S.A.", "237", "001100"),
				 Arguments.arguments("Nu Pagamentos S.A.", "260", "9999999")
				 );
	 }
	 
	/**
	 * Test method for {@link wallet.BankAccount#getAccAgency()}.
	 */
	 @ParameterizedTest
	 @MethodSource("testGetAccAgencyInputs")
	final void testGetAccAgency(String bkName, String bkId, String accAge) {
		 BankAccount bankAcc=new BankAccount(bkName, bkId, "01234", accAge);
		 assertEquals(accAge, bankAcc.getAccAgency());
	}
	 static Stream<Arguments> testGetAccAgencyInputs(){
		 return Stream.of(
				 Arguments.arguments("Banco Santander (Brasil) S.A.", "033", "123"),
				 Arguments.arguments("Banco Cooperativo Sicredi S.A.", "748", "99999"),
				 Arguments.arguments("Banco Bradesco S.A.", "237", "001100"),
				 Arguments.arguments("Nu Pagamentos S.A.", "260", "9999999")
				 );
	 }
	/**
	 * Test method for {@link wallet.BankAccount#getBankName()}.
	 */
	 @ParameterizedTest
	 @MethodSource("testGetBankNameInputs")
	final void testGetBankName(String bkName, String bkId) {
		 BankAccount bankAcc=new BankAccount(bkName, bkId, "01234", "0101");
		 assertEquals(bkName, bankAcc.getBankName());
	}
	 static Stream<Arguments> testGetBankNameInputs(){
		 return Stream.of(
				 Arguments.arguments("Banco Santander (Brasil) S.A.", "033"),
				 Arguments.arguments("Banco Cooperativo Sicredi S.A.", "748"),
				 Arguments.arguments("Banco Bradesco S.A.", "237"),
				 Arguments.arguments("Nu Pagamentos S.A.", "260")
				 );
	 }

}

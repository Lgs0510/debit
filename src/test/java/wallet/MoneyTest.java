/**
 * 
 */
package wallet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 
 */
public class MoneyTest {
	/**
	 * Test method for {@link wallet.Money#getCurrency()}.
	 */
	 @ParameterizedTest
	 @EnumSource(Currency_t.class)
	void testGetCurrency(Currency_t initialCurrency) {	
		Money coin = new Money(100, initialCurrency);
		assertEquals(initialCurrency, coin.getCurrency());
	}
	 
	/**
	 * Test method for {@link wallet.Money#setCurrency(wallet.Currency_t)}.
	 */
	 @ParameterizedTest
	 @MethodSource("setCurrencyTestInputs")
	 void testSetCurrency(Currency_t initCurrency, Currency_t setCurrency) {
			Money coin = new Money(0, initCurrency);
			coin.setCurrency(setCurrency);
			assertEquals(setCurrency, coin.getCurrency());
	}
	 
	 static Stream<Arguments> setCurrencyTestInputs(){
		 return Stream.of(
				 Arguments.arguments(Currency_t.BRL, Currency_t.USD),
				 Arguments.arguments(Currency_t.USD, Currency_t.EUR),
				 Arguments.arguments(Currency_t.EUR, Currency_t.BRL)
				 );
	 }

	/**
	 * Test method for {@link wallet.Money#getAmount()}.
	 */
	 @ParameterizedTest
	 @MethodSource("testGetAmountValuesInput")
	void testGetAmount(double initialAmount, Currency_t initCurrency) {
		Money coin = new Money(initialAmount, initCurrency);
		assertEquals(initialAmount, coin.getAmount(), 0.01);	
	}
	 
	 static Stream<Arguments> testGetAmountValuesInput(){
		 return Stream.of(
				 Arguments.arguments(0, Currency_t.USD),
				 Arguments.arguments(-1, Currency_t.EUR),
				 Arguments.arguments(0.01, Currency_t.BRL),
				 Arguments.arguments(2000000000, Currency_t.BRL)
				 );
	 }


	/**
	 * Test method for {@link wallet.Money#currencyMatch(wallet.Currency_t)}.
	 */
	 @ParameterizedTest
	 @MethodSource("testCurrencyMatchValuesInput")
	void testCurrencyMatch(Currency_t initCurrency, Currency_t currencyToCheck, boolean expectedResult) {
		Money coin = new Money(0, initCurrency);
		if(expectedResult){
			assertTrue(coin.currencyMatch(currencyToCheck));
		}
		else{
			assertFalse(coin.currencyMatch(currencyToCheck));
		}
	 }
	 static Stream<Arguments> testCurrencyMatchValuesInput(){
		 return Stream.of(
				 Arguments.arguments(Currency_t.USD, Currency_t.USD, true),
				 Arguments.arguments(Currency_t.EUR, Currency_t.EUR, true),
				 Arguments.arguments(Currency_t.BRL, Currency_t.USD, false),
				 Arguments.arguments(Currency_t.USD, null, false)
				 );
	 }

	/**
	 * Test method for {@link wallet.Money#currencyMatch(wallet.Currency_t)}.
	 */
	 @ParameterizedTest
	 @MethodSource("testConstInvalidParValInput")
	 void testConstructorInvalidParameters(double amount, Currency_t currency, RuntimeException exept, String msg) {
		 Exception exception = assertThrows(exept.getClass(), ()->{
			 new Money(amount, currency);
		 });
		 assertEquals(msg,exception.getMessage());
	 }
	 static Stream<Arguments> testConstInvalidParValInput(){
		 return Stream.of(
				 Arguments.arguments(0.001, Currency_t.USD, new IllegalArgumentException(), "Amount of money cannot be lesser then 1 cent ($0.01)"),
				 Arguments.arguments(2000000000.01, Currency_t.USD, new IllegalArgumentException(), "Amount of money cannot be bigger then 2 billion($2000000000.00)"),
				 Arguments.arguments(-0.001, Currency_t.USD, new IllegalArgumentException(), "Amount of money cannot be lesser then 1 cent ($0.01)"),
				 Arguments.arguments(-2000000000.01, Currency_t.USD, new IllegalArgumentException(), "Amount of money cannot be bigger then 2 billion($2000000000.00)"),
				 Arguments.arguments(100, null, new IllegalArgumentException(), "Invalid Currency!")
				 ); 
	 }
}

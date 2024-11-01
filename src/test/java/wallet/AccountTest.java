/**
 * 
 */
package wallet;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 
 */
class AccountTest {

	/**
	 * Test method for {@link wallet.Account#getAmount()}.
	 */
	 @ParameterizedTest
	 @MethodSource("testGetAmountInputs")
	final void testGetAmount(Money coin) {
		 Account acc = new Account(coin);
		 assertEquals(coin.getAmount(), acc.getAmount(), 0.01);
	}
	 
	 static Stream<Arguments> testGetAmountInputs(){
		 return Stream.of(
				 Arguments.arguments(new Money(0, Currency_t.BRL)),
				 Arguments.arguments(new Money(1234.56, Currency_t.BRL)),
				 Arguments.arguments(new Money(2000000000, Currency_t.BRL)),
				 Arguments.arguments(new Money(-2000000000, Currency_t.BRL)),
				 Arguments.arguments(new Money(0.01, Currency_t.BRL)),
				 Arguments.arguments(new Money(-0.01, Currency_t.BRL))
				 );
	 }

	/**
	 * Test method for {@link wallet.Account#addFunds(wallet.Money)}.
	 */
	 @ParameterizedTest
	 @MethodSource("testAddFundsInputs")
	final void testAddFunds(Money coin, Money addCoin) {
		Account acc = new Account(coin);
		acc.addFunds(addCoin);
		assertEquals(coin.getAmount() + addCoin.getAmount(), acc.getAmount(), 0.01);
	}
	 
	 static Stream<Arguments> testAddFundsInputs(){
		 return Stream.of(
				 Arguments.arguments(new Money(0, Currency_t.BRL), 			new Money(0, Currency_t.BRL)),
				 Arguments.arguments(new Money(0, Currency_t.EUR), 			new Money(1000, Currency_t.EUR)),
				 Arguments.arguments(new Money(1234.56, Currency_t.EUR), 	new Money(0, Currency_t.EUR)),
				 Arguments.arguments(new Money(2000000000, Currency_t.BRL),	new Money(0.01, Currency_t.BRL)),
				 Arguments.arguments(new Money(-2000000000, Currency_t.BRL),new Money(2000000000, Currency_t.BRL)),
				 Arguments.arguments(new Money(0.01, Currency_t.BRL),		new Money(2000000000, Currency_t.BRL)),
				 Arguments.arguments(new Money(-0.01, Currency_t.USD),		new Money(0.01, Currency_t.USD))
				 );
	 }

	/**
	 * Test method for {@link wallet.Account#withdrawFunds(wallet.Money)}.
	 */
	 @ParameterizedTest
	 @MethodSource("testWithdrawFundsInputs")
	final void testWithdrawFunds(Money coin, Money takeCoin) {
		Account acc = new Account(coin);
		acc.withdrawFunds(takeCoin);
		assertEquals(coin.getAmount() - takeCoin.getAmount(), acc.getAmount(), 0.01);
	}
	 static Stream<Arguments> testWithdrawFundsInputs(){
		 return Stream.of(
				 Arguments.arguments(new Money(0, Currency_t.BRL), 			new Money(0, Currency_t.BRL)),
				 Arguments.arguments(new Money(1000, Currency_t.EUR), 		new Money(0, Currency_t.EUR)),
				 Arguments.arguments(new Money(1234.56, Currency_t.EUR), 	new Money(1.56, Currency_t.EUR)),
				 Arguments.arguments(new Money(2000000000, Currency_t.BRL),	new Money(2000000000, Currency_t.BRL)),
				 Arguments.arguments(new Money(1, Currency_t.BRL),			new Money(1, Currency_t.BRL))
				 );
	 }

	 @ParameterizedTest
	 @MethodSource("testWithdrawFundsEInputs")
	final void testWithdrawFundsE(Money coin, Money takeCoin, RuntimeException exept, String msg) {
		 Account acc = new Account(coin);
		 Exception exception = assertThrows(exept.getClass(), ()->{
			 acc.withdrawFunds(takeCoin);
		 });
		 assertEquals(msg, exception.getMessage());
	}
	 static Stream<Arguments> testWithdrawFundsEInputs(){
		 return Stream.of(
				 Arguments.arguments(new Money(0, Currency_t.BRL), 			new Money(-100, Currency_t.BRL),new IllegalArgumentException(), "It is not possible to withdraw negative money!"),
				 Arguments.arguments(new Money(1000, Currency_t.EUR), 		new Money(2000, Currency_t.EUR),new IllegalArgumentException(),"It is not possible to withdraw more money than what is available!"),
				 Arguments.arguments(new Money(1234.56, Currency_t.EUR), 	new Money(1.56, Currency_t.USD),new IllegalArgumentException(),"It is not possible to retrieve money from other currencies right now!")
				 );
	 }
	 
	/**
	 * Test method for {@link wallet.Account#addFunds(wallet.Money)}.
	 */
	 @ParameterizedTest
	 @MethodSource("testAddFundsEInputs")
	final void testAddFundsE(Money coin, Money addCoin, RuntimeException exept, String msg) {
		 Account acc = new Account(coin);
		 Exception exception = assertThrows(exept.getClass(), ()->{
			 acc.addFunds(addCoin);
			 System.out.print(acc.getAmount());
		 });
		 
		 assertEquals(msg, exception.getMessage());
	}
	 static Stream<Arguments> testAddFundsEInputs(){
		 return Stream.of(
				 Arguments.arguments(new Money(0, Currency_t.BRL), 			new Money(-100, Currency_t.BRL),new IllegalArgumentException(), "It is not possible to add negative money!"),
				 Arguments.arguments(new Money(2000000000, Currency_t.EUR), new Money(2000000000.00, Currency_t.EUR),new IllegalArgumentException(),"Amount of money in the account cannot be bigger then 4 billion($4000000000.00)"),
				 Arguments.arguments(new Money(1234.56, Currency_t.EUR), 	new Money(0.1, Currency_t.USD),new IllegalArgumentException(),"It is not possible to add money from other currencies right now!")
				 );
	 }
	 @Test
	 final void testAccountIds() {
		 int i;
		 Account[] accArr = new Account[100];
		 ArrayList<Account> accList = new ArrayList<Account>();
		 for(i=0;i<accArr.length;i++) {
			 accArr[i]=new Account(Currency_t.BRL);
			 assertEquals(i, accArr[i].getAccID());
		 }
		 for(i=5; i < 30; i+=5) {
			 accArr[i].ClosseAccount();
			 accArr[i]=null;
			 accList.add(new Account(new Money(0,Currency_t.BRL)));
			 assertEquals(i, accList.get((i/5)-1).getAccID());
		 }
	 }
}

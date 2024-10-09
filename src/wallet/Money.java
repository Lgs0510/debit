package wallet;

public class Money {

	public Money(double amount) {
		if(amount < 0.01) {
			throw new IllegalArgumentException("Amount of monye cannot be lesser then 1 cent ($0.01)");
		}
		else if(amount > 2000000000) {
			throw new IllegalArgumentException("Amount of monye cannot be bigger then 2 billion($2000000000.00)");
		}
		amount=((int)Math.round(amount*100))/100;
	}

}

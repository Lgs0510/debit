/**
 * 
 */
package wallet;


/**
 * 
 */
public enum Currency_t {
	BRL,
	USD,
	EUR;
	
	public static boolean validValue(Object j) {
		if (j != null) {
			for(Currency_t coin : Currency_t.values()) {
				if(coin.valueOf(coin.name())== j) {
					return true;
				}
			}
		}
		return false;
	}
}

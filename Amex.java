package creditcardsystem;

public class Amex extends CreditCardStructure {
    public Amex(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        /* Implementation of the Discover credit card requirements,
         * First digit is a 3 and second digit a 4 or 7. Length is 15 digits.
         */
    	if (cardNumber == null) {
    		return false;
    	}
    	if (cardNumber.length() != 15) {
            return false;
        }
    	return (cardNumber.startsWith("34") || cardNumber.startsWith("37"));
    }
    @Override
    public String getCardType() {
        return "Amex";
    }
    
    public String getDetailedErrorMessage() {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return "Invalid: empty/null card number";
        }
        if (!cardNumber.matches("\\d+")) {
            return "Invalid: non numeric characters";
        }
        if (cardNumber.length() != 15) {
            return "Invalid: incorrect length";
        }
        if (!cardNumber.startsWith("34") && !cardNumber.startsWith("37")) {
            return "Invalid: does not start with '5'";
        }
        return "Valid";
    }
}
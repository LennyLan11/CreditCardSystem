package creditcardsystem;

public class Discover extends CreditCardStructure {
    public Discover(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        /* Implementation of the Discover credit card requirements,
         * First four digits are 6011. Length is 16 digits.
         */
    	if (cardNumber == null || !cardNumber.matches("\\d+")) {
    		return false;
    	}
    	if (cardNumber.length() != 16) {
            return false;
        }
        return cardNumber.startsWith("6011");
    }
    @Override
    public String getCardType() {
        return "Discover";
    }
    
    @Override
    public String getDetailedErrorMessage() {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return "Invalid: empty/null card number";
        }
        if (!cardNumber.matches("\\d+")) {
            return "Invalid: non numeric characters";
        }
        if (cardNumber.length() != 16) {
            return "Invalid: incorrect length";
        }
        if (!cardNumber.startsWith("6011")) {
            return "Invalid: does not start with '6011'";
        }
        return "Valid";
    }
}
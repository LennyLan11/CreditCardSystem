package creditcardsystem;

public class CreateErrorMsg extends CreditCardStructure {
    
    public CreateErrorMsg(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        // Since this is a generic card, it's considered not valid by default.
        return false;
    }

    @Override
    public String getCardType() {
        // This method could return "Unknown" or some other indicator.
        return "Unknown";
    }

    @Override
    public String getDetailedErrorMessage() {
        // Provide a detailed error message for the unknown card type.
        if (cardNumber == null || cardNumber.isEmpty()) {
            return "Invalid: empty/null card number";
        }
        if (cardNumber.length() > 16) {
        	return "Invalid: more than 16 digits";
        }
        // Additional checks can be added here if necessary.
        return "Invalid: not a possible card number";
    }
}


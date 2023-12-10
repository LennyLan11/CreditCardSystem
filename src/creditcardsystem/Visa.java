package creditcardsystem;

public class Visa extends CreditCardStructure {
    public Visa(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        // Implementation of the VISA credit card requirements,
        // First digit is a 4. Length is either 13 or 16 digits.
        return cardNumber != null || !cardNumber.matches("\\d+") &&
               (cardNumber.length() == 13 || cardNumber.length() == 16) &&
               cardNumber.startsWith("4");
    }

    @Override
    public String getCardType() {
        return "Visa";
    }
    
    @Override
    public String getDetailedErrorMessage() {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return "Invalid: empty/null card number";
        }
        if (!cardNumber.matches("\\d+")) {
            return "Invalid: non numeric characters";
        }
        if (cardNumber.length() != 13 && cardNumber.length() != 16) {
            return "Invalid: incorrect length";
        }
        if (!cardNumber.startsWith("4")) {
            return "Invalid: does not start with '4'";
        }
        return "Valid";
    }
}


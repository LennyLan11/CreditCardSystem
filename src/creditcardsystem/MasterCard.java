package creditcardsystem;

public class MasterCard extends CreditCardStructure {
    public MasterCard(String cardNumber, String expirationDate, String cardHolderName) {
        super(cardNumber, expirationDate, cardHolderName);
    }

    @Override
    public boolean isValid() {
        // Implementation of the MasterCard credit card requirements
        if (cardNumber == null || !cardNumber.matches("\\d+")) {
            return false;
        }
        if (cardNumber.length() != 16) {
            return false;
        }
        if (!cardNumber.startsWith("5")) { // Fix: Use '!' to check that it DOES start with '5'
            return false;
        }
        
        char secondDigit = cardNumber.charAt(1);
        if (secondDigit >= '1' && secondDigit <= '5') {
            return true; // The card is valid if the second digit is between '1' and '5'
        }
        return false; // The card is invalid if the second digit is not between '1' and '5'
    }
    @Override
    public String getCardType() {
        return "MasterCard";
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
        if (!cardNumber.startsWith("5")) {
            return "Invalid: does not start with '5'";
        }
        if (!(cardNumber.charAt(1) >= '1' && cardNumber.charAt(1) <= '5')) {
            return "Invalid: second digit must be between 1 and 5";
        }
        return "Valid";
    }
}

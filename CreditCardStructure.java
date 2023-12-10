package creditcardsystem;

public abstract class CreditCardStructure {
    protected String cardNumber;
    protected String expirationDate;
    protected String cardHolderName;

    public CreditCardStructure(String cardNumber, String expirationDate, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardHolderName = cardHolderName;
    }

    public abstract boolean isValid();
    public abstract String getDetailedErrorMessage();
    // Abstract method to be implemented by subclasses to return the card type
    public abstract String getCardType();

    // Standard getters for card details
    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }
    
    // Optionally, if there's common validation logic, you can add it here
    // For example, check if the card number is numeric and within valid length
    protected boolean isNumericAndWithinLength(int length) {
        if (cardNumber == null || cardNumber.length() != length) {
            return false;
        }
        for (char c : cardNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}


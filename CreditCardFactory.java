/**
 * 
 */
package creditcardsystem;

/**
 * @author haolan
 *
 */

public class CreditCardFactory {

    public static CreditCardStructure getCreditCard(String cardNumber, String expirationDate, String cardHolderName) {
        if (cardNumber == null) {
            return null;
        }

        if (cardNumber.startsWith("4")) {
            // Check for Visa card prefix and length
            if (cardNumber.length() == 13 || cardNumber.length() == 16) {
                return new Visa(cardNumber, expirationDate, cardHolderName);
            }
        } else if (cardNumber.startsWith("5")) {
            // Check for MasterCard prefix and length
            if (cardNumber.length() == 16 && cardNumber.matches("5[1-5]\\d{14}")) {
                return new MasterCard(cardNumber, expirationDate, cardHolderName);
            }
        } else if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            // Check for Amex prefix and length
            if (cardNumber.length() == 15) {
                return new Amex(cardNumber, expirationDate, cardHolderName);
            }
        } else if (cardNumber.startsWith("6011")) {
            // Check for Discover card prefix and length
            if (cardNumber.length() == 16) {
                return new Discover(cardNumber, expirationDate, cardHolderName);
            }
        }

        // If none of the conditions are met, return null
        return null;
    }
}

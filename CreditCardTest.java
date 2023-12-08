package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import creditcardsystem.Amex;
import creditcardsystem.Discover;
import creditcardsystem.MasterCard;
import creditcardsystem.Visa;

public class CreditCardTest {

    @Test
    public void testVisaCardValidation() {
        Visa validVisa13Digit = new Visa("4123456789012", "12/24", "Stephen Curry");
        Visa validVisa16Digit = new Visa("4123456789012345", "12/24", "Moses Moody");
        // credit card has invalid length
        Visa invalidVisa = new Visa("411111123432111111", "12/24", "Charlie Lee");

        assertTrue(validVisa13Digit.isValid());
        assertTrue(validVisa16Digit.isValid());
        assertFalse(invalidVisa.isValid());
    }

    @Test
    public void testMasterCardValidation() {
        MasterCard validMasterCard = new MasterCard("5123456789012345", "12/24", "Daves Andrew");
        // Invalid card: second digit is '9', outside the valid range '1' through '5'.
        MasterCard invalidMasterCard = new MasterCard("5923456789012345", "12/28", "Chris Paul");

        assertTrue(validMasterCard.isValid(), "The valid MasterCard number should be valid.");
        assertFalse(invalidMasterCard.isValid(), "The invalid MasterCard number should be invalid.");
    }

    @Test
    public void testAmexCardValidation() {
        Amex validAmex34 = new Amex("341234567890123", "12/24", "Grace Thompson");
        Amex validAmex37 = new Amex("371234567890123", "12/24", "Dario Saric");
        // credit card has invalid length
        Amex invalidAmex = new Amex("30111111111111", "12/24", "Ivan Eason");

        assertTrue(validAmex34.isValid());
        assertTrue(validAmex37.isValid());
        assertFalse(invalidAmex.isValid());
    }

    @Test
    public void testDiscoverCardValidation() {
        Discover validDiscover = new Discover("6011123456789012", "12/24", "Judy Zhang");
        // first four digits are not 6011
        Discover invalidDiscover = new Discover("7011111111111111", "12/24", "Kevon Looney");

        assertTrue(validDiscover.isValid());
        assertFalse(invalidDiscover.isValid());
    }

}

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_moreThan3Digits_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Age("100"));
    }

    @Test
    public void constructor_nonNumber_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Age("abc"));
    }

    @Test
    public void constructor_emptyString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Age(""));
    }

    @Test
    public void constructor_validAge_success() {
        assertEquals("1", new Age("1").value);
        assertEquals("25", new Age("25").value);
        assertEquals("99", new Age("99").value);
    }


    @Test
    public void isValidAge_nullAge_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Age.isValidAge(null));
    }

    @Test
    public void isValidAge_decimal_returnsFalse() {
        assertFalse(Age.isValidAge("1.5"));
    }

    @Test
    public void isValidAge_alphanumeric_returnsFalse() {
        assertFalse(Age.isValidAge("sadhsakhd2343"));
    }

    @Test
    public void isValidAge_validFormats_returnsTrue() {
        assertTrue(Age.isValidAge("1"));
        assertTrue(Age.isValidAge("0"));
        assertTrue(Age.isValidAge("99"));
        assertTrue(Age.isValidAge("10"));
        assertTrue(Age.isValidAge("9"));
    }

    @Test
    public void toString_returnsValue() {
        assertEquals("12", new Age("12").toString());
        assertEquals("1", new Age("1").toString());
        assertEquals("99", new Age("99").toString());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Age age = new Age("12");
        assertTrue(age.equals(age));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        assertEquals(new Age("12"), new Age("12"));
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        assertNotEquals(new Age("12"), new Age("13"));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(new Age("12").equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(new Age("12").equals("12"));
        assertFalse(new Age("12").equals(12));
    }

    @Test
    public void hashCode_sameValue_sameHashCode() {
        assertEquals(new Age("12").hashCode(), new Age("12").hashCode());
    }

    @Test
    public void hashCode_differentValue_differentHashCode() {
        // Not guaranteed by contract, but true for string-backed values
        assertNotEquals(new Age("12").hashCode(), new Age("99").hashCode());
    }
}

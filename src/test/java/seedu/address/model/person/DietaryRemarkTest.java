package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.remarks.DietaryRemark;

public class DietaryRemarkTest {

    @Test
    public void equals() {
        DietaryRemark dietaryRemark = new DietaryRemark("Hello");

        // same object -> returns true
        assertTrue(dietaryRemark.equals(dietaryRemark));

        // same values -> returns true
        DietaryRemark remarkCopy = new DietaryRemark(dietaryRemark.value);
        assertTrue(dietaryRemark.equals(remarkCopy));

        // different types -> returns false
        assertFalse(dietaryRemark.equals(1));

        // null -> returns false
        assertFalse(dietaryRemark.equals(null));

        // different remark -> returns false
        DietaryRemark differentRemark = new DietaryRemark("Bye");
        assertFalse(dietaryRemark.equals(differentRemark));
    }
}

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.remarks.BehaviorRemark;

public class BehaviorRemarkTest {

    @Test
    public void equals() {
        BehaviorRemark behaviorRemark = new BehaviorRemark("Hello");

        // same object -> returns true
        assertTrue(behaviorRemark.equals(behaviorRemark));

        // same values -> returns true
        BehaviorRemark remarkCopy = new BehaviorRemark(behaviorRemark.value);
        assertTrue(behaviorRemark.equals(remarkCopy));

        // different types -> returns false
        assertFalse(behaviorRemark.equals(1));

        // null -> returns false
        assertFalse(behaviorRemark.equals(null));

        // different remark -> returns false
        BehaviorRemark differentRemark = new BehaviorRemark("Bye");
        assertFalse(behaviorRemark.equals(differentRemark));
    }
}

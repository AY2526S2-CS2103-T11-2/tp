package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.remarks.ClassRemark;

public class ClassRemarkTest {

    @Test
    public void equals() {
        ClassRemark classRemark = new ClassRemark("Hello");

        // same object -> returns true
        assertTrue(classRemark.equals(classRemark));

        // same values -> returns true
        ClassRemark remarkCopy = new ClassRemark(classRemark.value);
        assertTrue(classRemark.equals(remarkCopy));

        // different types -> returns false
        assertFalse(classRemark.equals(1));

        // null -> returns false
        assertFalse(classRemark.equals(null));

        // different remark -> returns false
        ClassRemark differentRemark = new ClassRemark("Bye");
        assertFalse(classRemark.equals(differentRemark));
    }
}

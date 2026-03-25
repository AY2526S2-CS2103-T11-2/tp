package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName()
                + ", age=" + ALICE.getAge() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags()
                + ", parentName=" + ALICE.getParentName() + ", parentPhone=" + ALICE.getParentPhone()
                + ", parentEmail=" + ALICE.getParentEmail() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hasSameNormalizedName_sameName_returnsTrue() {
        Person p1 = new PersonBuilder().withName("John Doe").build();
        Person p2 = new PersonBuilder().withName("John Doe").build();

        assertTrue(p1.hasSimilarName(p2));
    }

    @Test
    public void hasSameNormalizedName_extraWhitespace_returnsTrue() {
        Person p1 = new PersonBuilder().withName("John   Doe").build();
        Person p2 = new PersonBuilder().withName("john doe  ").build();

        assertTrue(p1.hasSimilarName(p2));
    }

    @Test
    public void hasSameNormalizedName_differentNames_returnsFalse() {
        Person p1 = new PersonBuilder().withName("John Doe").build();
        Person p2 = new PersonBuilder().withName("Jane Doe").build();

        assertFalse(p1.hasSimilarName(p2));
    }

    @Test
    public void hasSameNormalizedName_null_throwsNullPointerException() {
        Person p = new PersonBuilder().withName("John Doe").build();

        assertThrows(NullPointerException.class, () -> p.hasSimilarName(null));
    }

    @Test
    public void hasSameNormalizedName_isSymmetric() {
        Person p1 = new PersonBuilder().withName("John   Doe").build();
        Person p2 = new PersonBuilder().withName("john doe").build();

        assertTrue(p1.hasSimilarName(p2));
        assertTrue(p2.hasSimilarName(p1));
    }

    @Test
    public void hasSameNormalizedName_multipleCalls_consistentResult() {
        Person p1 = new PersonBuilder().withName("John Doe").build();
        Person p2 = new PersonBuilder().withName("john doe").build();

        boolean first = p1.hasSimilarName(p2);
        boolean second = p1.hasSimilarName(p2);

        assertEquals(first, second);
    }

    @Test
    public void hasSameNormalizedName_sameObject_returnsTrue() {
        Person p = new PersonBuilder().withName("John Doe").build();

        assertTrue(p.hasSimilarName(p));
    }
}

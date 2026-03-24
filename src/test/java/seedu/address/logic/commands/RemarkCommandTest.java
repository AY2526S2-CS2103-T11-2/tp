package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BEHAVIORREMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BEHAVIORREMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSREMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSREMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIETARYREMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIETARYREMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.remarks.BehaviorRemark;
import seedu.address.model.person.remarks.ClassRemark;
import seedu.address.model.person.remarks.DietaryRemark;
import seedu.address.model.person.remarks.Remark;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {

    private static final String REMARK_STUB = "Some remark";
    private static final String DIETARYREMARK_STUB = "Some dietary remark";
    private static final String CLASSREMARK_STUB = "Some class remark";
    private static final String BEHAVIORREMARK_STUB = "Some behavior remark";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new Remark(editedPerson.getRemark().value))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new Remark(editedPerson.getRemark().value))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDietaryRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDietaryRemark(DIETARYREMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new DietaryRemark(editedPerson.getDietaryRemark().value))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_DIETARY_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteDietaryRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDietaryRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new DietaryRemark(editedPerson.getDietaryRemark().value))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_DIETARY_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addClassRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withClassRemark(CLASSREMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new ClassRemark(editedPerson.getClassRemark().value))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_CLASS_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteClassRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withClassRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new ClassRemark(editedPerson.getClassRemark().value))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_CLASS_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addBehaviorRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withBehaviorRemark(BEHAVIORREMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new BehaviorRemark(editedPerson.getBehaviorRemark().value))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_BEHAVIOR_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteBehaviorRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withBehaviorRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new BehaviorRemark(editedPerson.getBehaviorRemark().value))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_BEHAVIOR_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new Remark(editedPerson.getRemark().value))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_dietaryRemarkFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withDietaryRemark(DIETARYREMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new DietaryRemark(DIETARYREMARK_STUB))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_DIETARY_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_classRemarkFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withClassRemark(CLASSREMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new ClassRemark(CLASSREMARK_STUB))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_CLASS_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_behaviorRemarkFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withBehaviorRemark(BEHAVIORREMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new BehaviorRemark(BEHAVIORREMARK_STUB))));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_BEHAVIOR_REMARK_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex,
                new ArrayList<>(Arrays.asList(
                        new Remark(VALID_REMARK_BOB))));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex,
                new ArrayList<>(Arrays.asList(
                        new Remark(VALID_REMARK_BOB))));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new Remark(VALID_REMARK_AMY))));
        final RemarkCommand dietaryCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new DietaryRemark(VALID_DIETARYREMARK_AMY))));
        final RemarkCommand classCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new ClassRemark(VALID_CLASSREMARK_AMY))));
        final RemarkCommand behaviorCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new BehaviorRemark(VALID_BEHAVIORREMARK_AMY))));

        // same values -> returns true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new Remark(VALID_REMARK_AMY))));
        assertTrue(standardCommand.equals(commandWithSameValues));
        RemarkCommand dietaryCommandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new DietaryRemark(VALID_DIETARYREMARK_AMY))));
        assertTrue(dietaryCommand.equals(dietaryCommandWithSameValues));
        RemarkCommand classCommandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new ClassRemark(VALID_CLASSREMARK_AMY))));
        assertTrue(classCommand.equals(classCommandWithSameValues));
        RemarkCommand behaviorCommandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new BehaviorRemark(VALID_BEHAVIORREMARK_AMY))));
        assertTrue(behaviorCommand.equals(behaviorCommandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        assertTrue(dietaryCommand.equals(dietaryCommand));
        assertTrue(classCommand.equals(classCommand));
        assertTrue(behaviorCommand.equals(behaviorCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));
        assertFalse(dietaryCommand.equals(null));
        assertFalse(classCommand.equals(null));
        assertFalse(behaviorCommand.equals(null));


        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        assertFalse(dietaryCommand.equals(new ClearCommand()));
        assertFalse(classCommand.equals(new ClearCommand()));
        assertFalse(behaviorCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON,
                new ArrayList<>(Arrays.asList(
                        new Remark(VALID_REMARK_AMY))))));
        assertFalse(dietaryCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON,
                new ArrayList<>(Arrays.asList(
                        new DietaryRemark(VALID_DIETARYREMARK_AMY))))));
        assertFalse(classCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON,
                new ArrayList<>(Arrays.asList(
                        new ClassRemark(VALID_CLASSREMARK_AMY))))));
        assertFalse(behaviorCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON,
                new ArrayList<>(Arrays.asList(
                        new BehaviorRemark(VALID_BEHAVIORREMARK_AMY))))));


        // different remark -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new Remark(VALID_REMARK_BOB))))));
        assertFalse(dietaryCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new DietaryRemark(VALID_DIETARYREMARK_BOB))))));
        assertFalse(classCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new ClassRemark(VALID_CLASSREMARK_BOB))))));
        assertFalse(behaviorCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(
                        new BehaviorRemark(VALID_BEHAVIORREMARK_BOB))))));
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class ImportCommandTest {

    @TempDir
    Path tempDir;

    @Test
    public void execute_validCsv_importsAndSkipsDuplicates() throws Exception {
        Path csvPath = tempDir.resolve("persons.csv");
        Files.writeString(csvPath,
            "name,age,address,parentName,parentPhone,parentEmail,tags,remark,dietaryRemark,classRemark,"
                + "behaviorRemark\n"
                + "Alice Tan,10,123 Street,Jane Tan,91234567,jane@example.com,friend;classA,"
                + "Takes initiative,Vegetarian,P3-A,Very cooperative\n"
                + "Alice Tan,10,123 Street,Jane Tan,91234567,jane@example.com,friend,,,,\n"
                + "Bob Lee,11,456 Street,John Lee,92345678,john@example.com,,Needs support,,,\n");

        ModelStubAcceptingPersons modelStub = new ModelStubAcceptingPersons();
        CommandResult result = new ImportCommand(csvPath).execute(modelStub);

        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 2, 1), result.getFeedbackToUser());
        assertEquals(2, modelStub.personsAdded.size());
        assertTrue(modelStub.personsAdded.get(0).getTags().contains(new Tag("friend")));
        assertTrue(modelStub.personsAdded.get(0).getTags().contains(new Tag("classA")));
        assertEquals("Takes initiative", modelStub.personsAdded.get(0).getRemark().value);
        assertEquals("Vegetarian", modelStub.personsAdded.get(0).getDietaryRemark().value);
        assertEquals("P3-A", modelStub.personsAdded.get(0).getClassRemark().value);
        assertEquals("Very cooperative", modelStub.personsAdded.get(0).getBehaviorRemark().value);
        assertEquals("Needs support", modelStub.personsAdded.get(1).getRemark().value);
    }

    @Test
    public void execute_sevenColumnCsvTreatsSeventhColumnAsTags() throws Exception {
        Path csvPath = tempDir.resolve("remarks.csv");
        Files.writeString(csvPath,
                "name,age,address,parentName,parentPhone,parentEmail,optional\n"
                        + "Cara Lim,9,10 Street,Mum Lim,93456789,mum@example.com,Needs close monitoring\n");

        ImportCommand importCommand = new ImportCommand(csvPath);

        assertThrows(CommandException.class,
            String.format(ImportCommand.MESSAGE_CSV_INVALID_FORMAT, 2, Tag.MESSAGE_CONSTRAINTS), () ->
                    importCommand.execute(new ModelStubAcceptingPersons()));
    }

    @Test
    public void execute_invalidRow_throwsCommandException() throws IOException {
        Path csvPath = tempDir.resolve("invalid.csv");
        Files.writeString(csvPath,
                "name,age,address,parentName,parentPhone,parentEmail\n"
                        + "Alice Tan,10,123 Street,Jane Tan,91234567,not-an-email\n");

        ImportCommand importCommand = new ImportCommand(csvPath);

        assertThrows(CommandException.class,
            String.format(ImportCommand.MESSAGE_CSV_INVALID_FORMAT, 2, Email.MESSAGE_CONSTRAINTS), () ->
                    importCommand.execute(new ModelStubAcceptingPersons()));
    }

    @Test
    public void execute_missingFile_throwsCommandException() {
        Path missingPath = tempDir.resolve("does-not-exist.csv");
        ImportCommand importCommand = new ImportCommand(missingPath);

        assertThrows(CommandException.class,
            String.format(ImportCommand.MESSAGE_FILE_READ_ERROR, missingPath), () ->
                importCommand.execute(new ModelStubAcceptingPersons()));
    }

    private static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        public boolean hasSimilarPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        public void sortFilteredPersonListByName() {
            throw new AssertionError("This method should not be called.");
        }

        public void sortFilteredPersonListByAge() {
            throw new AssertionError("This method should not be called.");
        }

        public void sortFilteredPersonListByParentName() {
            throw new AssertionError("This method should not be called.");
        }

        public void sortFilteredPersonListByParentPhone() {
            throw new AssertionError("This method should not be called.");
        }

        public void sortFilteredPersonListByParentEmail() {
            throw new AssertionError("This method should not be called.");
        }

        public void clearFilteredPersonListSorting() {
            throw new AssertionError("This method should not be called.");
        }
    }

    private static class ModelStubAcceptingPersons extends ModelStub {
        private final List<Person> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}

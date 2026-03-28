package seedu.address.logic.importer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class CsvPersonRowMapperTest {

    private final CsvPersonRowMapper rowMapper = new CsvPersonRowMapper();

    @Test
    public void map_validRowWithOptionalFields_success() throws Exception {
        List<String> fields = Arrays.asList(
                "Alice Tan", "10", "123 Street", "Jane Tan", "91234567", "jane@example.com",
                "friend;classA", "Takes initiative", "Vegetarian", "P3-A", "Very cooperative");

        Person person = rowMapper.map(fields, 2);

        assertEquals("Alice Tan", person.getName().fullName);
        assertTrue(person.getTags().contains(new Tag("friend")));
        assertTrue(person.getTags().contains(new Tag("classA")));
        assertEquals("Takes initiative", person.getRemark().value);
        assertEquals("Vegetarian", person.getDietaryRemark().value);
        assertEquals("P3-A", person.getClassRemark().value);
        assertEquals("Very cooperative", person.getBehaviorRemark().value);
    }

    @Test
    public void map_emptyOptionalFields_defaultsToEmptyValues() throws Exception {
        List<String> fields = Arrays.asList(
                "Bob Lee", "11", "456 Street", "John Lee", "92345678", "john@example.com",
                "", "", "", "", "");

        Person person = rowMapper.map(fields, 4);

        assertTrue(person.getTags().isEmpty());
        assertEquals("", person.getRemark().value);
        assertEquals("", person.getDietaryRemark().value);
        assertEquals("", person.getClassRemark().value);
        assertEquals("", person.getBehaviorRemark().value);
    }

    @Test
    public void map_tooFewColumns_throwsCommandException() {
        List<String> fields = List.of("Alice Tan", "10");

        assertThrows(CommandException.class,
                String.format(CsvPersonImporter.MESSAGE_CSV_INVALID_FORMAT, 3,
                        "Expected 6 to 11 columns but found 2"), () -> rowMapper.map(fields, 3));
    }

    @Test
    public void map_invalidCoreField_throwsCommandException() {
        List<String> fields = Arrays.asList(
                "Alice Tan", "10", "123 Street", "Jane Tan", "91234567", "not-an-email");

        assertThrows(CommandException.class,
                String.format(CsvPersonImporter.MESSAGE_CSV_INVALID_FORMAT, 5, Email.MESSAGE_CONSTRAINTS), () ->
                        rowMapper.map(fields, 5));
    }
}

package seedu.address.logic.importer;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class CsvPersonImporterTest {

    @TempDir
    Path tempDir;

    @Test
    public void constructor_nullRowMapper_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CsvPersonImporter(null));
    }

    @Test
    public void read_missingFile_throwsCommandException() {
        CsvPersonImporter importer = new CsvPersonImporter();
        Path missingPath = tempDir.resolve("missing.csv");

        assertThrows(CommandException.class, String.format(CsvPersonImporter.MESSAGE_FILE_READ_ERROR, missingPath), ()
            -> importer.read(missingPath));
    }

    @Test
    public void read_headerAndBlankLines_onlyDataRowsPassedToMapper() throws Exception {
        Path csvPath = tempDir.resolve("rows.csv");
        Files.writeString(csvPath,
                "name,age,address,parentName,parentPhone,parentEmail\n"
                        + "\n"
                        + "Alice Tan,10,123 Street,Jane Tan,91234567,jane@example.com\n"
                        + "\n"
                        + "Bob Lee,11,456 Street,John Lee,92345678,john@example.com\n");

        RecordingRowMapper rowMapper = new RecordingRowMapper();
        rowMapper.addPersonToReturn(TypicalPersons.ALICE);
        rowMapper.addPersonToReturn(TypicalPersons.BOB);
        CsvPersonImporter importer = new CsvPersonImporter(rowMapper);

        List<Person> persons = importer.read(csvPath);

        assertEquals(2, persons.size());
        assertEquals(List.of(3, 5), rowMapper.lineNumbersPassed);
        assertEquals("Alice Tan", rowMapper.firstFieldValuesPassed.get(0));
        assertEquals("Bob Lee", rowMapper.firstFieldValuesPassed.get(1));
    }

    @Test
    public void read_unclosedQuotedField_throwsCommandExceptionWithLineNumber() throws Exception {
        Path csvPath = tempDir.resolve("bad-quotes.csv");
        Files.writeString(csvPath,
                "name,age,address,parentName,parentPhone,parentEmail\n"
                        + "\"Alice Tan,10,123 Street,Jane Tan,91234567,jane@example.com\n");

        CsvPersonImporter importer = new CsvPersonImporter();

        assertThrows(CommandException.class,
                String.format(CsvPersonImporter.MESSAGE_CSV_INVALID_FORMAT, 2,
                "CSV row has unclosed quoted field"), () -> importer.read(csvPath));
    }

    @Test
    public void read_quotedComma_preservesSingleFieldValue() throws Exception {
        Path csvPath = tempDir.resolve("quoted-comma.csv");
        Files.writeString(csvPath,
            "name,age,address,parentName,parentPhone,parentEmail\n"
                + "\"Tan, Alice\",10,123 Street,Jane Tan,91234567,jane@example.com\n");

        RecordingRowMapper rowMapper = new RecordingRowMapper();
        rowMapper.addPersonToReturn(TypicalPersons.ALICE);
        CsvPersonImporter importer = new CsvPersonImporter(rowMapper);

        importer.read(csvPath);

        assertEquals(1, rowMapper.parsedRowsPassed.size());
        assertEquals("Tan, Alice", rowMapper.parsedRowsPassed.get(0).get(0));
        assertEquals(Arrays.asList(
            "Tan, Alice", "10", "123 Street", "Jane Tan", "91234567", "jane@example.com"),
            rowMapper.parsedRowsPassed.get(0));
    }

    @Test
    public void read_escapedQuotes_unescapesQuoteCharacters() throws Exception {
        Path csvPath = tempDir.resolve("escaped-quotes.csv");
        Files.writeString(csvPath,
            "name,age,address,parentName,parentPhone,parentEmail,tags,remark\n"
                + "Alice Tan,10,123 Street,Jane Tan,91234567,jane@example.com,,\"He said \"\"Hi\"\"\"\n");

        RecordingRowMapper rowMapper = new RecordingRowMapper();
        rowMapper.addPersonToReturn(TypicalPersons.ALICE);
        CsvPersonImporter importer = new CsvPersonImporter(rowMapper);

        importer.read(csvPath);

        assertEquals(1, rowMapper.parsedRowsPassed.size());
        assertEquals("He said \"Hi\"", rowMapper.parsedRowsPassed.get(0).get(7));
    }

    private static class RecordingRowMapper extends CsvPersonRowMapper {
        private final List<Integer> lineNumbersPassed = new ArrayList<>();
        private final List<String> firstFieldValuesPassed = new ArrayList<>();
        private final List<List<String>> parsedRowsPassed = new ArrayList<>();
        private final List<Person> personsToReturn = new ArrayList<>();

        private void addPersonToReturn(Person person) {
            requireNonNull(person);
            personsToReturn.add(person);
        }

        @Override
        public Person map(List<String> fields, int lineNumber) {
            lineNumbersPassed.add(lineNumber);
            firstFieldValuesPassed.add(fields.get(0));
            parsedRowsPassed.add(new ArrayList<>(fields));
            return personsToReturn.get(lineNumbersPassed.size() - 1);
        }
    }
}

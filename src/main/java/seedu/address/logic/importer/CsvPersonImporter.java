package seedu.address.logic.importer;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Reads persons from CSV files.
 */
public class CsvPersonImporter {

    public static final String MESSAGE_FILE_READ_ERROR = "Unable to read CSV file: %1$s";
    public static final String MESSAGE_CSV_INVALID_FORMAT = "Invalid CSV format at line %1$d: %2$s";

    private static final char CSV_DELIMITER = ',';
    private static final char CSV_QUOTE = '"';
    private static final int MIN_COLUMN_COUNT = 6;

    private final CsvPersonRowMapper rowMapper;

    /**
     * Creates a CSV importer with a default row mapper.
     */
    public CsvPersonImporter() {
        this(new CsvPersonRowMapper());
    }

    /**
     * Creates a CSV importer with a custom row mapper.
     */
    public CsvPersonImporter(CsvPersonRowMapper rowMapper) {
        requireNonNull(rowMapper);
        this.rowMapper = rowMapper;
    }

    /**
     * Reads a CSV file and parses all data rows into persons.
     *
     * @param path file path to read from
     * @return list of parsed persons
     * @throws CommandException if the file cannot be read or contains invalid CSV data
     */
    public List<Person> read(Path path) throws CommandException {
        requireNonNull(path);

        final List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            throw new CommandException(String.format(MESSAGE_FILE_READ_ERROR, path), ioe);
        }

        List<Person> persons = new ArrayList<>();
        boolean isFirstNonEmptyLine = true;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.trim().isEmpty()) {
                continue;
            }

            int lineNumber = i + 1;
            List<String> fields = splitCsvLine(line, lineNumber);

            if (isFirstNonEmptyLine && isHeader(fields)) {
                isFirstNonEmptyLine = false;
                continue;
            }

            isFirstNonEmptyLine = false;
            persons.add(rowMapper.map(fields, lineNumber));
        }

        return persons;
    }

    /**
     * Splits a single CSV row into fields.
     *
     * @param line raw CSV row
     * @param lineNumber line number in source file for error reporting
     * @return list of trimmed field values
     * @throws CommandException if a quoted field is not closed
     */
    private List<String> splitCsvLine(String line, int lineNumber) throws CommandException {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);

            if (currentChar == CSV_QUOTE && isEscapedQuote(line, i, inQuotes)) {
                currentField.append(CSV_QUOTE);
                i++;
                continue;
            }

            if (currentChar == CSV_QUOTE) {
                inQuotes = !inQuotes;
                continue;
            }

            if (currentChar == CSV_DELIMITER && !inQuotes) {
                fields.add(currentField.toString().trim());
                currentField.setLength(0);
                continue;
            }

            currentField.append(currentChar);
        }

        if (inQuotes) {
            throw new CommandException(String.format(MESSAGE_CSV_INVALID_FORMAT, lineNumber,
                    "CSV row has unclosed quoted field"));
        }

        fields.add(currentField.toString().trim());
        return fields;
    }

    /**
     * Returns true when the current quote is an escaped quote sequence ({@code ""})
     * inside a quoted field.
     */
    private boolean isEscapedQuote(String line, int index, boolean inQuotes) {
        return inQuotes
                && index + 1 < line.length()
                && line.charAt(index + 1) == CSV_QUOTE;
    }

    /**
     * Returns true if the row matches the supported header prefix
     * ({@code name,age,address,parentName,parentPhone,parentEmail}).
     */
    private boolean isHeader(List<String> fields) {
        if (fields.size() < MIN_COLUMN_COUNT) {
            return false;
        }

        return normalized(fields.get(0)).equals("name")
                && normalized(fields.get(1)).equals("age")
                && normalized(fields.get(2)).equals("address")
                && normalized(fields.get(3)).equals("parentname")
                && normalized(fields.get(4)).equals("parentphone")
                && normalized(fields.get(5)).equals("parentemail");
    }

    /**
     * Normalizes header values for case-insensitive and whitespace-insensitive comparison.
     */
    private String normalized(String value) {
        return value.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "");
    }
}

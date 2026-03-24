package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.remarks.BehaviorRemark;
import seedu.address.model.person.remarks.ClassRemark;
import seedu.address.model.person.remarks.DietaryRemark;
import seedu.address.model.person.remarks.Remark;
import seedu.address.model.tag.Tag;

/**
 * Imports persons from a CSV file into the address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports persons from a CSV file. "
            + "Parameters: FILE_PATH\n"
            + "CSV columns (fixed indices): "
            + "0=name,1=age,2=address,3=parentName,4=parentPhone,5=parentEmail,"
            + "6=tags,7=remark,8=dietaryRemark,9=classRemark,10=behaviorRemark\n"
            + "Leave optional fields empty in CSV if not needed (for example: ',,').\n"
            + "If present, tags should be separated by semicolons ';'.\n"
            + "Example: " + COMMAND_WORD + " data/contacts.csv";

    public static final String MESSAGE_FILE_READ_ERROR = "Unable to read CSV file: %1$s";
    public static final String MESSAGE_CSV_INVALID_FORMAT = "Invalid CSV format at line %1$d: %2$s";
    public static final String MESSAGE_SUCCESS = "Imported %1$d person(s). Skipped %2$d duplicate person(s).";

    private static final char CSV_DELIMITER = ',';
    private static final char CSV_QUOTE = '"';

    private static final int MIN_COLUMN_COUNT = 6;
    private static final int MAX_COLUMN_COUNT = 11;
    private static final int TAGS_COLUMN_INDEX = 6;
    private static final int REMARK_COLUMN_INDEX = 7;
    private static final int DIETARY_REMARK_COLUMN_INDEX = 8;
    private static final int CLASS_REMARK_COLUMN_INDEX = 9;
    private static final int BEHAVIOR_REMARK_COLUMN_INDEX = 10;

    private final Path csvFilePath;

    /**
     * @param csvFilePath path of the CSV file to import from
     */
    public ImportCommand(Path csvFilePath) {
        requireNonNull(csvFilePath);
        this.csvFilePath = csvFilePath;
    }

    /**
     * Executes the import command by reading persons from the configured CSV file
     * and adding non-duplicate persons to the model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback containing imported and skipped duplicate counts
     * @throws CommandException if the CSV cannot be read or contains invalid data
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> importedPersons = readPersonsFromCsv(csvFilePath);
        int importedCount = 0;
        int skippedDuplicates = 0;

        for (Person person : importedPersons) {
            if (model.hasPerson(person)) {
                skippedDuplicates++;
                continue;
            }

            model.addPerson(person);
            importedCount++;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, importedCount, skippedDuplicates));
    }

    /**
     * Reads the CSV file and converts each data row into a {@code Person}.
     *
     * @param path file path to read from
     * @return list of persons parsed from the CSV
     * @throws CommandException if the file cannot be read or a row is invalid
     */
    private List<Person> readPersonsFromCsv(Path path) throws CommandException {
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
            List<String> fields = splitCsvLine(line);

            if (isFirstNonEmptyLine && isHeader(fields)) {
                isFirstNonEmptyLine = false;
                continue;
            }

            isFirstNonEmptyLine = false;
            persons.add(parsePerson(fields, lineNumber));
        }

        return persons;
    }

    /**
     * Parses one CSV row into a {@code Person}.
     *
     * @param fields parsed CSV columns for one row
     * @param lineNumber original CSV line number (1-based), used for error reporting
     * @return person built from the row data
     * @throws CommandException if the row contains invalid column count or invalid field values
     */
    private Person parsePerson(List<String> fields, int lineNumber) throws CommandException {
        if (fields.size() < MIN_COLUMN_COUNT || fields.size() > MAX_COLUMN_COUNT) {
            throw new CommandException(String.format(MESSAGE_CSV_INVALID_FORMAT, lineNumber,
                    "Expected 6 to 11 columns but found " + fields.size()));
        }

        try {
            Name name = ParserUtil.parseName(fields.get(0));
            Age age = ParserUtil.parseAge(fields.get(1));
            Address address = ParserUtil.parseAddress(fields.get(2));
            Name parentName = ParserUtil.parseName(fields.get(3));
            Phone parentPhone = ParserUtil.parsePhone(fields.get(4));
            Email parentEmail = ParserUtil.parseEmail(fields.get(5));

            ParsedOptionalFields optionalFields = parseOptionalFields(fields);

            return new Person(name, age, address, optionalFields.tags,
                    parentName, parentPhone, parentEmail,
                    new Remark(optionalFields.remark),
                    new DietaryRemark(optionalFields.dietaryRemark),
                    new ClassRemark(optionalFields.classRemark),
                    new BehaviorRemark(optionalFields.behaviorRemark));
        } catch (ParseException pe) {
            throw new CommandException(String.format(MESSAGE_CSV_INVALID_FORMAT, lineNumber, pe.getMessage()), pe);
        }
    }

    /**
     * Parses optional fields from fixed CSV indices.
     *
     * @param fields parsed CSV columns for one row
     * @return parsed optional fields container
     * @throws ParseException if the tags column contains invalid tag values
     */
    private ParsedOptionalFields parseOptionalFields(List<String> fields) throws ParseException {
        Set<Tag> tags = new HashSet<>();
        String remark = "";
        String dietaryRemark = "";
        String classRemark = "";
        String behaviorRemark = "";

        if (fields.size() <= TAGS_COLUMN_INDEX) {
            return new ParsedOptionalFields(tags, remark, dietaryRemark, classRemark, behaviorRemark);
        }

        tags = parseTags(fields.get(TAGS_COLUMN_INDEX));
        remark = getOptionalField(fields, REMARK_COLUMN_INDEX);
        dietaryRemark = getOptionalField(fields, DIETARY_REMARK_COLUMN_INDEX);
        classRemark = getOptionalField(fields, CLASS_REMARK_COLUMN_INDEX);
        behaviorRemark = getOptionalField(fields, BEHAVIOR_REMARK_COLUMN_INDEX);

        return new ParsedOptionalFields(tags, remark, dietaryRemark, classRemark, behaviorRemark);
    }

    /**
     * Returns an optional field value by index, defaulting to an empty string when absent.
     */
    private String getOptionalField(List<String> fields, int index) {
        if (fields.size() <= index) {
            return "";
        }
        return fields.get(index);
    }

    /**
     * Parses the tags cell into a set of {@code Tag}. Empty input yields an empty set.
     *
     * @param rawTags raw tags cell value, where tags are separated by semicolons
     * @return parsed tag set
     * @throws ParseException if any tag is invalid
     */
    private Set<Tag> parseTags(String rawTags) throws ParseException {
        Set<Tag> tags = new HashSet<>();

        if (rawTags == null || rawTags.trim().isEmpty()) {
            return tags;
        }

        String[] splitTags = rawTags.split(";");
        for (String splitTag : splitTags) {
            String trimmedTag = splitTag.trim();
            if (trimmedTag.isEmpty()) {
                continue;
            }
            tags.add(ParserUtil.parseTag(trimmedTag));
        }
        return tags;
    }

    /**
     * Splits one CSV line into fields, supporting quoted values and escaped quotes.
     *
     * @param line one raw CSV line
     * @return parsed fields for the line
     * @throws CommandException if the line has an unclosed quoted field
     */
    private List<String> splitCsvLine(String line) throws CommandException {
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
            throw new CommandException("CSV row has unclosed quoted field");
        }

        fields.add(currentField.toString().trim());
        return fields;
    }

    /**
     * Returns true if the current index points to an escaped quote ("")
     * in a quoted field.
     */
    private boolean isEscapedQuote(String line, int index, boolean inQuotes) {
        return inQuotes
                && index + 1 < line.length()
                && line.charAt(index + 1) == CSV_QUOTE;
    }

    /**
     * Returns true if the row matches the supported CSV header prefix
     * (name through parentEmail columns).
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
     * Normalizes a header value by trimming, lowercasing, and removing internal whitespace.
     */
    private String normalized(String value) {
        return value.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", "");
    }

    private static class ParsedOptionalFields {
        private final Set<Tag> tags;
        private final String remark;
        private final String dietaryRemark;
        private final String classRemark;
        private final String behaviorRemark;

        /**
         * Creates a container for optional fields parsed from a CSV row.
         */
        private ParsedOptionalFields(Set<Tag> tags, String remark, String dietaryRemark,
                                     String classRemark, String behaviorRemark) {
            this.tags = tags;
            this.remark = remark;
            this.dietaryRemark = dietaryRemark;
            this.classRemark = classRemark;
            this.behaviorRemark = behaviorRemark;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return csvFilePath.equals(otherImportCommand.csvFilePath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("csvFilePath", csvFilePath)
                .toString();
    }
}

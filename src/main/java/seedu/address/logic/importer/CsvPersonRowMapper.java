package seedu.address.logic.importer;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
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
 * Maps tokenized CSV rows to {@code Person} objects.
 */
public class CsvPersonRowMapper {

    private static final int MIN_COLUMN_COUNT = 6;
    private static final int MAX_COLUMN_COUNT = 11;
    private static final int TAGS_COLUMN_INDEX = 6;
    private static final int REMARK_COLUMN_INDEX = 7;
    private static final int DIETARY_REMARK_COLUMN_INDEX = 8;
    private static final int CLASS_REMARK_COLUMN_INDEX = 9;
    private static final int BEHAVIOR_REMARK_COLUMN_INDEX = 10;

    /**
     * Parses one tokenized CSV row into a {@code Person}.
     *
     * @param fields tokenized CSV columns
     * @param lineNumber source line number for error reporting
     * @return person parsed from the row
     * @throws CommandException if the row contains invalid column count or invalid field values
     */
    public Person map(List<String> fields, int lineNumber) throws CommandException {
        requireNonNull(fields);

        if (fields.size() < MIN_COLUMN_COUNT || fields.size() > MAX_COLUMN_COUNT) {
            throw new CommandException(String.format(CsvPersonImporter.MESSAGE_CSV_INVALID_FORMAT, lineNumber,
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
            throw new CommandException(
                    String.format(CsvPersonImporter.MESSAGE_CSV_INVALID_FORMAT, lineNumber, pe.getMessage()), pe);
        }
    }

    /**
     * Parses {@code List<String> fields} into a {@code ParsedOptionalFields}.
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
     * Returns a field value by index, or an empty string when the column is absent.
     */
    private String getOptionalField(List<String> fields, int index) {
        if (fields.size() <= index) {
            return "";
        }
        return fields.get(index);
    }

    /**
     * Parses {@code String rawTags} into a {@code Set<Tag>}.
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
     * Immutable container for optional CSV fields.
     */
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
}

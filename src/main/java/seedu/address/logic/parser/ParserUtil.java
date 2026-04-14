package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_INDEX_RANGE =
            "Index range must be in the format START-END (e.g. 1-3),\n"
                    + "where both are positive integers and the start is less than or equal to the end.";
    public static final String RANGE_SEPARATOR = "-";
    public static final String WHITESPACE_REGEX = "\\s+";

    private static final int RANGE_PARTS_COUNT = 2;
    private static final int START_INDEX_POSITION = 0;
    private static final int END_INDEX_POSITION = 1;
    private static final int MAX_DELETION_CAPACITY = 1000;
    private static final String MESSAGE_MAX_DELETION_CAPACITY_EXCEEDED =
            "Range too large. Please specify a range of at most "
                    + MAX_DELETION_CAPACITY + " indices.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE));
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a range string (e.g., "3-5") into a list of Index objects.
     *
     * @param rangePart The string containing the hyphenated range.
     * @return A list of Index objects within the specified range.
     * @throws ParseException if the format is invalid or start index is greater than end index.
     */
    public static List<Index> parseRange(String rangePart) throws ParseException {
        //GitHub Copilot was used as a debugging aid during the development of this method.
        // It assisted in highlighting possible edge cases and refining exception handling logic.
        // The final implementation reflects independent verification and modifications by the author.
        String[] rangeValues = rangePart.split(RANGE_SEPARATOR);

        if (rangeValues.length != RANGE_PARTS_COUNT) {
            throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
        }

        Index start = ParserUtil.parseIndex(rangeValues[START_INDEX_POSITION]);
        Index end = ParserUtil.parseIndex(rangeValues[END_INDEX_POSITION]);

        if (start.getZeroBased() > end.getZeroBased()) {
            throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
        }

        int numPersonsToDelete = end.getZeroBased() - start.getZeroBased() + 1;
        if (numPersonsToDelete > MAX_DELETION_CAPACITY) {
            throw new ParseException(MESSAGE_MAX_DELETION_CAPACITY_EXCEEDED);
        }

        List<Index> rangeIndices = new ArrayList<>();
        for (int i = start.getOneBased(); i <= end.getOneBased(); i++) {
            rangeIndices.add(Index.fromOneBased(i));
        }

        return rangeIndices;
    }

    /**
     * Parses the arguments string into a list of Index objects.
     *
     * @param args The non-empty trimmed argument string.
     * @return A list of unique Index objects.
     * @throws ParseException if any part of the string is not a valid index or range.
     */
    public static List<Index> parseIndices(String args) throws ParseException {
        List<Index> indices = new ArrayList<>();
        String[] parts = args.split(WHITESPACE_REGEX);

        for (String part : parts) {
            if (part.contains(RANGE_SEPARATOR)) {
                indices.addAll(parseRange(part));
            } else {
                indices.add(ParserUtil.parseIndex(part));
            }
        }

        return indices;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String age} into a {@code Age}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code age} is invalid.
     */
    public static Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        String trimmedAge = age.trim();
        if (!Age.isValidAge(trimmedAge)) {
            throw new ParseException(Age.MESSAGE_CONSTRAINTS);
        }
        return new Age(trimmedAge);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}

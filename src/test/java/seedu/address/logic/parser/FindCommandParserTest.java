package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEHAVIOR_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIETARY_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Age;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // 1. Multiple keywords for a single prefix
        Map<Prefix, List<String>> multiKeywordMap = new HashMap<>();
        multiKeywordMap.put(PREFIX_NAME, Arrays.asList("Alice", "Bob"));
        assertParseSuccess(parser, " n/Alice Bob", new FindCommand(
                new NameContainsKeywordsPredicate(multiKeywordMap)));

        // 2. Duplicate prefixes (n/Alice n/Bob)
        Map<Prefix, List<String>> duplicatePrefixMap = new HashMap<>();
        duplicatePrefixMap.put(PREFIX_NAME, Arrays.asList("Alice", "Bob"));
        assertParseSuccess(parser, " n/Alice n/Bob", new FindCommand(
                new NameContainsKeywordsPredicate(duplicatePrefixMap)));

        // 3. Short Phone Number
        Map<Prefix, List<String>> phoneMap = new HashMap<>();
        phoneMap.put(PREFIX_PARENT_PHONE, Arrays.asList("91"));
        assertParseSuccess(parser, " pc/91", new FindCommand(
                new NameContainsKeywordsPredicate(phoneMap)));
    }

    @Test
    public void parse_illegalPreamble_throwsParseException() {
        // This currently triggers checkForInvalidPrefixes
        assertParseFailure(parser, " Alice/Bob", "Unknown prefix detected: Alice/Bob"
                + "\nPlease use only valid prefixes (n/, pn/, a/, etc.)");
    }

    @Test
    public void parse_allPrefixes_success() {
        Prefix[] allPrefixes = {
            PREFIX_NAME, PREFIX_ADDRESS, PREFIX_AGE, PREFIX_TAG,
            PREFIX_REMARK, PREFIX_DIETARY_REMARK, PREFIX_CLASS_REMARK,
            PREFIX_BEHAVIOR_REMARK, PREFIX_PARENT_NAME, PREFIX_PARENT_PHONE,
            PREFIX_PARENT_EMAIL
        };

        for (Prefix p : allPrefixes) {
            String input = " " + p.getPrefix() + "testValue";
            if (p.equals(PREFIX_AGE)) {
                input = " a/12";
            }
            if (p.equals(PREFIX_PARENT_PHONE)) {
                input = " pc/98765432";
            }
            if (p.equals(PREFIX_PARENT_EMAIL)) {
                input = " pe/test@email.com";
            }

            final String finalInput = input;
            org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> parser.parse(finalInput));
        }
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " unknown/", "Unknown prefix detected: unknown/"
                + "\nPlease use only valid prefixes (n/, pn/, a/, etc.)");

        assertParseFailure(parser, " dave/ ", "Unknown prefix detected: dave/"
                + "\nPlease use only valid prefixes (n/, pn/, a/, etc.)");
    }

    @Test
    public void parse_invalidValue_throwsParseException() {
        // 1. Invalid format for specific types
        assertParseFailure(parser, " a/notAnAge", Age.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " pc/notAPhone", "Phone numbers should only contain digits.");

        // 2. Empty values (after the prefix)
        assertParseFailure(parser, " n/ ", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " a/ ", Age.MESSAGE_CONSTRAINTS);

        // 3. Mixed valid and invalid
        // Should fail as soon as the first invalid value (a/abc) is encountered
        assertParseFailure(parser, " n/Alice a/abc", Age.MESSAGE_CONSTRAINTS);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // No prefixes - should treat as student name keywords
        List<String> nameKeywords = Arrays.asList("Alice", "Bob");
        List<String> emptyParentKeywords = Collections.emptyList();
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(nameKeywords, emptyParentKeywords));

        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // Multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);

        // Prefix search - parent name
        List<String> parentKeywords = Arrays.asList("Tan", "Smith");
        FindCommand expectedParentCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Collections.emptyList(), parentKeywords));

        assertParseSuccess(parser, " pn/Tan pn/Smith", expectedParentCommand);
    }

    @Test
    public void parse_invalidPrefixArgs_throwsParseException() {
        // Empty student name prefix
        assertParseFailure(parser, " n/", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Empty parent name prefix
        assertParseFailure(parser, " pn/", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // One valid, one empty
        assertParseFailure(parser, " n/Alice pn/", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}

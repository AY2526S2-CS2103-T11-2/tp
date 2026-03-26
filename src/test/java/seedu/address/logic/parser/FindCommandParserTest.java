package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIETARY_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
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
        // No prefixes - should treat as student name keywords in the map
        Map<Prefix, List<String>> nameMap = new HashMap<>();
        nameMap.put(PREFIX_NAME, Arrays.asList("Alice", "Bob"));
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(nameMap));

        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // Multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);

        // Prefix search - parent name
        Map<Prefix, List<String>> parentMap = new HashMap<>();
        parentMap.put(PREFIX_PARENT_NAME, Arrays.asList("Tan", "Smith"));
        FindCommand expectedParentCommand =
                new FindCommand(new NameContainsKeywordsPredicate(parentMap));

        assertParseSuccess(parser, " pn/Tan pn/Smith", expectedParentCommand);

        // Multi-prefix search (Age and Dietary Remark) ---
        Map<Prefix, List<String>> multiMap = new HashMap<>();
        multiMap.put(PREFIX_AGE, Arrays.asList("12"));
        multiMap.put(PREFIX_DIETARY_REMARK, Arrays.asList("Vegan"));
        FindCommand expectedMultiCommand =
                new FindCommand(new NameContainsKeywordsPredicate(multiMap));

        assertParseSuccess(parser, " a/12 d/Vegan", expectedMultiCommand);
    }

    @Test
    public void parse_invalidPrefixArgs_throwsParseException() {
        // Empty student name prefix
        assertParseFailure(parser, " n/", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Empty parent name prefix
        assertParseFailure(parser, " pn/", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // One valid, one empty (Dietary remark empty)
        assertParseFailure(parser, " n/Alice d/", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}

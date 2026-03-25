package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        // Single index
        assertParseSuccess(parser, "1", new DeleteCommand(List.of(INDEX_FIRST_PERSON)));

        // Multiple indices
        List<Index> multipleIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "1 2", new DeleteCommand(multipleIndices));

        // Range
        List<Index> rangeIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "1-3", new DeleteCommand(rangeIndices));

        // Mixed indices and ranges
        // Input: "1 3-4" -> Indices 1, 3, 4
        List<Index> mixedIndices = Arrays.asList(
                INDEX_FIRST_PERSON,
                Index.fromOneBased(3),
                Index.fromOneBased(4)
        );
        assertParseSuccess(parser, "1 3-4", new DeleteCommand(mixedIndices));


    }

    @Test
    public void parse_duplicatedArgs_returnsDeleteCommand() {
        List<Index> multipleIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "2 1 1-2", new DeleteCommand(multipleIndices));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty string
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // Whitespace only
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // Invalid characters (alphabets)
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // Invalid characters (symbols)
        assertParseFailure(parser, "1,2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // Invalid range format (too many hyphens)
        assertParseFailure(parser, "1-2-3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // Invalid range (start index > end index)
        assertParseFailure(parser, "3-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // Out of range index
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // Mixing valid and invalid parts
        assertParseFailure(parser, "1 abc 3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
    }
}

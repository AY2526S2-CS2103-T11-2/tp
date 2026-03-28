package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {

    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validPath_success() {
        assertParseSuccess(parser, " data/contacts.csv", new ImportCommand(Paths.get("data/contacts.csv")));
    }

    @Test
    public void parse_quotedPath_success() {
        assertParseSuccess(parser, " \"data/my contacts.csv\"",
                new ImportCommand(Paths.get("data/my contacts.csv")));
    }

    @Test
    public void parse_emptyInput_failure() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyQuotedPath_failure() {
        assertParseFailure(parser, " \"\" ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;

public class RemarkCommandTest {
    private Model model;
    private String  MESSAGE_NOT_IMPLEMENTED_YET;

    @Test
    public void execute() {
        assertCommandFailure(new RemarkCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        AddressBookParser parser = new AddressBookParser();
        assertTrue(parser.parseCommand(RemarkCommand.COMMAND_WORD) instanceof RemarkCommand);
    }
}

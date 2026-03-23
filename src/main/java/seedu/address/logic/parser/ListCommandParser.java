package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListCommand.SortParameter;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            // No sort parameter provided, display in default order
            return new ListCommand();
        } else if (trimmedArgs.equals(PREFIX_NAME.toString())) {
            return new ListCommand(SortParameter.NAME);
        } else if (trimmedArgs.equals(PREFIX_AGE.toString())) {
            return new ListCommand(SortParameter.AGE);
        } else if (trimmedArgs.equals(PREFIX_PARENT_NAME.toString())) {
            return new ListCommand(SortParameter.PARENT_NAME);
        } else if (trimmedArgs.equals(PREFIX_PARENT_PHONE.toString())) {
            return new ListCommand(SortParameter.PARENT_PHONE);
        } else if (trimmedArgs.equals(PREFIX_PARENT_EMAIL.toString())) {
            return new ListCommand(SortParameter.PARENT_EMAIL);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }
}

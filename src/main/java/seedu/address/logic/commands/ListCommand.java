package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists your contacts, and optionally sorts. "
            + "Use at most one of the following parameters: "
            + PREFIX_NAME + " | "
            + PREFIX_AGE + " | "
            + PREFIX_PARENT_NAME + " | "
            + PREFIX_PARENT_PHONE + " | "
            + PREFIX_PARENT_EMAIL + "\n"
            + "Examples:\n"
            + COMMAND_WORD + " " + PREFIX_NAME + "\n"
            + COMMAND_WORD;

    public enum SortParameter {
        NAME, AGE, PARENT_NAME, PARENT_PHONE, PARENT_EMAIL
    }

    private final SortParameter sortParameter;

    public ListCommand() {
        this.sortParameter = null;
    }

    public ListCommand(SortParameter sortParameter) {
        this.sortParameter = sortParameter;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

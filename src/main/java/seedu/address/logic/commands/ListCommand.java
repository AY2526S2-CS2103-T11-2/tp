package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons, sorted by: ";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists your contacts. Sorts by the specified parameter if provided.\n"
            + "Use at most one of the following parameters: "
            + PREFIX_NAME + " | "
            + PREFIX_AGE + " | "
            + PREFIX_PARENT_NAME + " | "
            + PREFIX_PARENT_PHONE + " | "
            + PREFIX_PARENT_EMAIL + "\n"
            + "Examples:\n"
            + COMMAND_WORD + " " + PREFIX_NAME + "\n"
            + COMMAND_WORD;

    /**
     * Represents the contact's field that the list command should sort with.
     */
    public enum SortParameter {
        NAME("name"),
        AGE("age"),
        PARENT_NAME("parent name"),
        PARENT_PHONE("parent phone"),
        PARENT_EMAIL("parent email"),
        NONE("no order applied");

        private final String parameterName;

        SortParameter(String parameterName) {
            this.parameterName = parameterName;
        }

        @Override
        public String toString() {
            return this.parameterName;
        }
    }

    private final SortParameter sortParameter;

    public ListCommand() {
        this.sortParameter = SortParameter.NONE;
    }

    public ListCommand(SortParameter sortParameter) {
        this.sortParameter = sortParameter;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert sortParameter != null : "Sort parameter should never be null, it should use the default order at least";
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        switch (sortParameter) {
        case NAME:
            model.sortFilteredPersonListByName();
            break;
        case AGE:
            model.sortFilteredPersonListByAge();
            break;
        case PARENT_NAME:
            model.sortFilteredPersonListByParentName();
            break;
        case PARENT_PHONE:
            model.sortFilteredPersonListByParentPhone();
            break;
        case PARENT_EMAIL:
            model.sortFilteredPersonListByParentEmail();
            break;
        default:
            model.clearFilteredPersonListSorting();
        }

        return new CommandResult(MESSAGE_SUCCESS + sortParameter);
    }
}

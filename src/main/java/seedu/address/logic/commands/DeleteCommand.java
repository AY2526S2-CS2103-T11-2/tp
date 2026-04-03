package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person(s) identified by the index number(s) used in the displayed person list.\n"
            + "Parameters: INDEX [INDEX]... [START-END]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 3-5 7";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted: %1$s";

    private final List<Index> targetIndices;

    /**
     * Constructs a {@code DeleteCommand} with the given {@code targetIndices}.
     * <p>
     * The provided list of indices is deduplicated (to prevent multiple deletions of the same
     * person) and sorted in ascending order. Sorting ensures consistent internal state and
     * predictable behavior during execution and testing.
     * </p>
     *
     * @param targetIndices The indices of the persons in the filtered person list to be deleted.
     * @throws NullPointerException if {@code targetIndices} is null.
     */
    public DeleteCommand(List<Index> targetIndices) {
        requireNonNull(targetIndices);
        assert !targetIndices.isEmpty();

        Set<Index> uniqueIndices = new HashSet<>(targetIndices);
        List<Index> sortedIndices = new ArrayList<>(uniqueIndices);
        Collections.sort(sortedIndices,
                Comparator.comparingInt(Index::getZeroBased));

        this.targetIndices = sortedIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> personsToDelete = new ArrayList<>();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personsToDelete.add(lastShownList.get(targetIndex.getZeroBased()));
        }


        for (Person personToDelete: personsToDelete) {
            model.deletePerson(personToDelete);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.formatPersonList(personsToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndices)
                .toString();
    }
}

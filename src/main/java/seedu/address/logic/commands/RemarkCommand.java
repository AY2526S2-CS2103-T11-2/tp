package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEHAVIOR_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIETARY_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.remarks.BehaviorRemark;
import seedu.address.model.person.remarks.ClassRemark;
import seedu.address.model.person.remarks.DietaryRemark;
import seedu.address.model.person.remarks.Remark;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK] "
            + PREFIX_DIETARY_REMARK + "[DIETARY REMARK] "
            + PREFIX_BEHAVIOR_REMARK + "[BEHAVIOR REMARK] "
            + PREFIX_CLASS_REMARK + "[CLASS].\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim. "
            + PREFIX_DIETARY_REMARK + "Salmon.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Student: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Student: %1$s";
    public static final String MESSAGE_ADD_DIETARY_REMARK_SUCCESS = "Added dietary information to Student: %1$s";
    public static final String MESSAGE_DELETE_DIETARY_REMARK_SUCCESS = "Removed dietary information from Student: %1$s";
    public static final String MESSAGE_ADD_CLASS_REMARK_SUCCESS = "Added class to Student: %1$s";
    public static final String MESSAGE_DELETE_CLASS_REMARK_SUCCESS = "Removed class from Student: %1$s";
    public static final String MESSAGE_ADD_BEHAVIOR_REMARK_SUCCESS = "Added behavior information to Student: %1$s";
    public static final String MESSAGE_DELETE_BEHAVIOR_REMARK_SUCCESS =
            "Removed behavior information from Student: %1$s";


    private final Index index;
    private final List<Remark> remarks;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param remarks of the person to be updated to
     */
    public RemarkCommand(Index index, List<Remark> remarks) {
        requireAllNonNull(index, remarks);

        this.index = index;
        this.remarks = remarks;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Remark updatedRemark = personToEdit.getRemark();
        DietaryRemark updatedDietaryRemark = personToEdit.getDietaryRemark();
        ClassRemark updatedClassRemark = personToEdit.getClassRemark();
        BehaviorRemark updatedBehaviorRemark = personToEdit.getBehaviorRemark();

        for (Remark r : remarks) {
            if (r instanceof DietaryRemark) {
                updatedDietaryRemark = (DietaryRemark) r;
            } else if (r instanceof ClassRemark) {
                updatedClassRemark = (ClassRemark) r;
            } else if (r instanceof BehaviorRemark) {
                updatedBehaviorRemark = (BehaviorRemark) r;
            } else {
                updatedRemark = r;
            }
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getAge(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getParentName(),
                personToEdit.getParentPhone(), personToEdit.getParentEmail(),
                updatedRemark, updatedDietaryRemark, updatedClassRemark, updatedBehaviorRemark);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether the remarks are added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        List<String> messages = new ArrayList<>();

        for (Remark remark : remarks) {
            if (remark instanceof DietaryRemark) {
                messages.add(remark.value.isEmpty()
                        ? MESSAGE_DELETE_DIETARY_REMARK_SUCCESS
                        : MESSAGE_ADD_DIETARY_REMARK_SUCCESS);
            } else if (remark instanceof ClassRemark) {
                messages.add(remark.value.isEmpty()
                        ? MESSAGE_DELETE_CLASS_REMARK_SUCCESS
                        : MESSAGE_ADD_CLASS_REMARK_SUCCESS);
            } else if (remark instanceof BehaviorRemark) {
                messages.add(remark.value.isEmpty()
                        ? MESSAGE_DELETE_BEHAVIOR_REMARK_SUCCESS
                        : MESSAGE_ADD_BEHAVIOR_REMARK_SUCCESS);
            } else {
                messages.add(remark.value.isEmpty()
                        ? MESSAGE_DELETE_REMARK_SUCCESS
                        : MESSAGE_ADD_REMARK_SUCCESS);
            }
        }

        String message = String.join("\n", messages);
        return String.format(message, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remarks.equals(e.remarks);
    }
}

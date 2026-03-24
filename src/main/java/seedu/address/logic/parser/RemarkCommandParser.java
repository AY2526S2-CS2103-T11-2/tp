package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEHAVIOR_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIETARY_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.remarks.BehaviorRemark;
import seedu.address.model.person.remarks.ClassRemark;
import seedu.address.model.person.remarks.DietaryRemark;
import seedu.address.model.person.remarks.Remark;


/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code RemarkCommand}
     * and returns a {@code RemarkCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK, PREFIX_DIETARY_REMARK,
                PREFIX_CLASS_REMARK, PREFIX_BEHAVIOR_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), ive);
        }

        List<Remark> remarks = new ArrayList<>();

        argMultimap.getValue(PREFIX_DIETARY_REMARK).ifPresent(text ->
                remarks.add(new DietaryRemark(text))
        );

        argMultimap.getValue(PREFIX_CLASS_REMARK).ifPresent(text ->
                remarks.add(new ClassRemark(text))
        );

        argMultimap.getValue(PREFIX_BEHAVIOR_REMARK).ifPresent(text ->
                remarks.add(new BehaviorRemark(text))
        );

        argMultimap.getValue(PREFIX_REMARK).ifPresent(text ->
                remarks.add(new Remark(text))
        );

        if (remarks.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        return new RemarkCommand(index, remarks);
    }
}

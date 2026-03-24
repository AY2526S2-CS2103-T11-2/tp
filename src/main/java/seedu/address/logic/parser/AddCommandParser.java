package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.remarks.BehaviorRemark;
import seedu.address.model.person.remarks.ClassRemark;
import seedu.address.model.person.remarks.DietaryRemark;
import seedu.address.model.person.remarks.Remark;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_AGE, PREFIX_ADDRESS,
                PREFIX_PARENT_NAME, PREFIX_PARENT_PHONE, PREFIX_PARENT_EMAIL, PREFIX_TAG);

        // Ensure duplicate check includes parent fields too
        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NAME, PREFIX_AGE, PREFIX_ADDRESS,
                PREFIX_PARENT_NAME, PREFIX_PARENT_PHONE, PREFIX_PARENT_EMAIL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_AGE, PREFIX_ADDRESS,
                PREFIX_PARENT_NAME, PREFIX_PARENT_PHONE, PREFIX_PARENT_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Age age = ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Remark remark = new Remark(""); // add command does not allow adding remarks straight away
        DietaryRemark dietaryRemark = new DietaryRemark("");
        ClassRemark classRemark = new ClassRemark("");
        BehaviorRemark behaviorRemark = new BehaviorRemark("");
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Name parentName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PARENT_NAME).get());
        Phone parentPhone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PARENT_PHONE).get());
        Email parentEmail = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_PARENT_EMAIL).get());

        Person person = new Person(name, age, address, tagList, parentName, parentPhone, parentEmail, remark,
                dietaryRemark, classRemark, behaviorRemark);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_NAME;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PARENT_NAME);

        List<String> nameKeywords;
        List<String> parentKeywords;

        boolean hasNamePrefix = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean hasParentPrefix = argMultimap.getValue(PREFIX_PARENT_NAME).isPresent();

        if (!hasNamePrefix && !hasParentPrefix) {
            // OPTION 1: No prefixes used.
            // Treat the whole input as student name keywords (Legacy style).
            nameKeywords = Arrays.asList(trimmedArgs.split("\\s+"));
            parentKeywords = Collections.emptyList();
        } else {
            // OPTION 2: Prefixes are used.
            // Extract specifically what the user asked for.
            nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
            parentKeywords = argMultimap.getAllValues(PREFIX_PARENT_NAME);
        }

        return new FindCommand(new NameContainsKeywordsPredicate(nameKeywords, parentKeywords));
    }
}

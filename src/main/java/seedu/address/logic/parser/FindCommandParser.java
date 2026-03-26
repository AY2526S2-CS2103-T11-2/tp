package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEHAVIOR_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIETARY_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final String SPLIT_BY_WHITESPACE = "\\s+";
    /**
     * Array of all prefixes that the FindCommand can search by.
     */
    private static final Prefix[] ALLOWED_PREFIXES = {
        PREFIX_NAME, PREFIX_ADDRESS, PREFIX_AGE, PREFIX_TAG,
        PREFIX_REMARK, PREFIX_DIETARY_REMARK, PREFIX_CLASS_REMARK,
        PREFIX_BEHAVIOR_REMARK, PREFIX_PARENT_NAME, PREFIX_PARENT_PHONE,
        PREFIX_PARENT_EMAIL
    };

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, ALLOWED_PREFIXES);
        Map<Prefix, List<String>> keywordsMap = new HashMap<>();

        // Check if any prefixes were actually used in the command
        boolean anyPrefixPresent = Arrays.stream(ALLOWED_PREFIXES)
                .anyMatch(prefix -> argMultimap.getValue(prefix).isPresent());

        if (!anyPrefixPresent) {
            // OPTION 1: Legacy search (No prefixes used)
            // Default to searching student names with the entire input string
            keywordsMap.put(PREFIX_NAME, Arrays.asList(trimmedArgs.split(SPLIT_BY_WHITESPACE)));
        } else {
            // OPTION 2: Prefix-based search
            for (Prefix prefix : ALLOWED_PREFIXES) {
                if (argMultimap.getValue(prefix).isPresent()) {
                    List<String> keywords = argMultimap.getAllValues(prefix);

                    // Validation: Throws error if user wrote "n/" but provided no name
                    if (keywords.stream().anyMatch(String::isEmpty)) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                    }
                    keywordsMap.put(prefix, keywords);
                }
            }
        }

        return new FindCommand(new NameContainsKeywordsPredicate(keywordsMap));
    }
}

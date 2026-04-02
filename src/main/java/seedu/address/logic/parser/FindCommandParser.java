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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        assert args != null : "args should not be null";
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        // 1. Check for invalid prefixes (any word ending in / that isn't allowed)
        checkForInvalidPrefixes(trimmedArgs);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, ALLOWED_PREFIXES);
        assert argMultimap != null : "Tokenizer should always return a map";

        Map<Prefix, List<String>> keywordsMap = new HashMap<>();

        // 2. Handle Preamble
        String preamble = argMultimap.getPreamble().trim();
        if (!preamble.isEmpty()) {
            if (preamble.contains("/")) {
                throw new ParseException("Invalid prefix detected or unauthorized use of forward slash.");
            }
            ParserUtil.parseName(preamble);
            keywordsMap.put(PREFIX_NAME, new ArrayList<>(Arrays.asList(preamble.split(SPLIT_BY_WHITESPACE))));
        }

        // 3. Process and Validate Allowed Prefixes
        for (Prefix prefix : ALLOWED_PREFIXES) {
            if (argMultimap.getValue(prefix).isPresent()) {
                List<String> rawValues = argMultimap.getAllValues(prefix);
                assert !rawValues.isEmpty() : "rawValues cannot be empty if prefix is present";

                for (String val : rawValues) {
                    validateValueByPrefix(prefix, val);
                }

                List<String> keywords = rawValues.stream()
                        .flatMap(s -> Arrays.stream(s.trim().split(SPLIT_BY_WHITESPACE)))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());

                if (prefix.equals(PREFIX_NAME)) {
                    List<String> existing = keywordsMap.getOrDefault(PREFIX_NAME, new ArrayList<>());
                    existing.addAll(keywords);
                    keywordsMap.put(PREFIX_NAME, existing);
                } else {
                    keywordsMap.put(prefix, keywords);
                }
            }
        }

        assert !keywordsMap.isEmpty() : "keywordsMap should contain at least one entry at this point";
        return new FindCommand(new NameContainsKeywordsPredicate(keywordsMap));
    }

    /**
     * Scans the input for any "word/" patterns that are not in the ALLOWED_PREFIXES list.
     */
    private void checkForInvalidPrefixes(String args) throws ParseException {
        String[] words = args.split(SPLIT_BY_WHITESPACE);
        for (String word : words) {
            if (word.contains("/")) {
                boolean isAllowed = Arrays.stream(ALLOWED_PREFIXES)
                        .anyMatch(p -> word.startsWith(p.getPrefix()));
                if (!isAllowed) {
                    throw new ParseException("Unknown prefix detected: " + word
                            + "\nPlease use only valid prefixes (n/, pn/, a/, etc.)");
                }
            }
        }
    }

    private void validateValueByPrefix(Prefix prefix, String value) throws ParseException {
        if (prefix.equals(PREFIX_NAME) || prefix.equals(PREFIX_PARENT_NAME)) {
            ParserUtil.parseName(value);
        } else if (prefix.equals(PREFIX_AGE)) {
            ParserUtil.parseAge(value);
        } else if (prefix.equals(PREFIX_ADDRESS)) {
            ParserUtil.parseAddress(value);
        } else if (prefix.equals(PREFIX_PARENT_PHONE)) {
            ParserUtil.parsePhone(value);
        } else if (prefix.equals(PREFIX_PARENT_EMAIL)) {
            ParserUtil.parseEmail(value);
        } else if (prefix.equals(PREFIX_TAG)) {
            ParserUtil.parseTag(value);
        }
    }
}

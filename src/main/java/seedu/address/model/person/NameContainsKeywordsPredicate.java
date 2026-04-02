package seedu.address.model.person;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.Prefix;

/**
 * Tests that a {@code Person}'s attributes match the keywords provided by the user.
 * Each key in the map represents a specific prefix (e.g., n/, a/, pn/), and the
 * associated list contains the search keywords input by the user for that field.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final Map<Prefix, List<String>> keywordsMap;

    /**
     * Constructs a {@code NameContainsKeywordsPredicate} that only filters by the person's name.
     * The parent keywords list will be initialized as empty.
     *
     * @param keywords A list of strings to match against a person's name.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywordsMap = new HashMap<>();
        if (!keywords.isEmpty()) {
            this.keywordsMap.put(PREFIX_NAME, keywords);
        }
    }

    /**
     * @param keywordsMap A map where each key is a prefix and the value is a list of
     *                    user-provided keywords to match against the corresponding
     *                    field in the Address Book.
     */
    public NameContainsKeywordsPredicate(Map<Prefix, List<String>> keywordsMap) {
        this.keywordsMap = keywordsMap;
    }

    @Override
    public boolean test(Person person) {
        assert person != null : "Person to test cannot be null";

        // Returns true if ANY prefix in the map has a keyword that matches the person's corresponding field.
        return keywordsMap.entrySet().stream().anyMatch(entry -> {
            Prefix prefix = entry.getKey();
            List<String> keywords = entry.getValue();

            assert keywords != null && !keywords.isEmpty() : "Keywords list in map should not be null or empty";

            String fieldToSearch = getFieldByPrefix(prefix, person);
            assert fieldToSearch != null : "Field to search extracted from person should not be null";

            return keywords.stream().anyMatch(keyword -> {
                // Exact match for Age to avoid "1" matching "12"
                if (prefix.equals(PREFIX_AGE)) {
                    return fieldToSearch.equals(keyword);
                }
                return fieldToSearch.toLowerCase().contains(keyword.toLowerCase());
            });
        });
    }

    /**
     * Helper method to extract the string value of a specific field from a Person
     * based on the provided Prefix.
     */
    private String getFieldByPrefix(Prefix prefix, Person person) {
        if (prefix.equals(PREFIX_NAME)) {
            return person.getName().fullName;
        }
        if (prefix.equals(PREFIX_AGE)) {
            return person.getAge().value;
        }
        if (prefix.equals(PREFIX_ADDRESS)) {
            return person.getAddress().value;
        }
        if (prefix.equals(PREFIX_PARENT_NAME)) {
            return person.getParentName().fullName;
        }
        if (prefix.equals(PREFIX_PARENT_PHONE)) {
            return person.getParentPhone().value;
        }
        if (prefix.equals(PREFIX_PARENT_EMAIL)) {
            return person.getParentEmail().value;
        }
        if (prefix.equals(PREFIX_REMARK)) {
            return person.getRemark().value;
        }
        if (prefix.equals(PREFIX_DIETARY_REMARK)) {
            return person.getDietaryRemark().value;
        }
        if (prefix.equals(PREFIX_CLASS_REMARK)) {
            return person.getClassRemark().value;
        }
        if (prefix.equals(PREFIX_BEHAVIOR_REMARK)) {
            return person.getBehaviorRemark().value;
        }
        if (prefix.equals(PREFIX_TAG)) {
            return getTagsAsString(person);
        }
        return "";
    }

    /**
     * Converts a person's tags into a space-separated string for keyword matching.
     */
    private String getTagsAsString(Person person) {
        return person.getTags().stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.joining(" "));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherPredicate = (NameContainsKeywordsPredicate) other;
        return keywordsMap.equals(otherPredicate.keywordsMap);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywordsMap", keywordsMap)
                .toString();
    }
}

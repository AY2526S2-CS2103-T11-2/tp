package seedu.address.model.person;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> parentKeywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this(keywords, Collections.emptyList());
    }

    public NameContainsKeywordsPredicate(List<String> nameKeywords, List<String> parentKeywords) {
        this.nameKeywords = nameKeywords;
        this.parentKeywords = parentKeywords;
    }

    @Override
    public boolean test(Person person) {
        boolean nameMatch = !nameKeywords.isEmpty() && nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));

        boolean parentMatch = !parentKeywords.isEmpty() && parentKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getParentName().fullName, keyword));

        return nameMatch || parentMatch;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherPredicate = (NameContainsKeywordsPredicate) other;
        return nameKeywords.equals(otherPredicate.nameKeywords)
                && parentKeywords.equals(otherPredicate.parentKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nameKeywords", nameKeywords)
                .add("parentKeywords", parentKeywords)
                .toString();
    }
}

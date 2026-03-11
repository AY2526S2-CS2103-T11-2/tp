package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstList = Collections.singletonList("first");
        List<String> secondList = Arrays.asList("first", "second");

        // student name predicates
        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(
                firstList, Collections.emptyList());
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(
                secondList, Collections.emptyList());

        // parent name predicates
        NameContainsKeywordsPredicate parentPredicate = new NameContainsKeywordsPredicate(
                Collections.emptyList(), firstList);

        // same values -> returns true
        assertTrue(firstPredicate.equals(new NameContainsKeywordsPredicate(firstList, Collections.emptyList())));

        // different parent name list -> returns false
        assertFalse(firstPredicate.equals(parentPredicate));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> nameKeywords = List.of("keyword1");
        List<String> parentKeywords = List.of("keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(nameKeywords, parentKeywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{"
                + "nameKeywords=" + nameKeywords + ", "
                + "parentKeywords=" + parentKeywords + "}";
        assertEquals(expected, predicate.toString());
    }
}

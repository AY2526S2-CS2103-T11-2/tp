package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIETARY_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Map<Prefix, List<String>> firstPredicateMap = new HashMap<>();
        firstPredicateMap.put(PREFIX_NAME, Collections.singletonList("first"));

        Map<Prefix, List<String>> secondPredicateMap = new HashMap<>();
        secondPredicateMap.put(PREFIX_NAME, Arrays.asList("first", "second"));

        Map<Prefix, List<String>> parentPredicateMap = new HashMap<>();
        parentPredicateMap.put(PREFIX_PARENT_NAME, Collections.singletonList("first"));

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateMap);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateMap);
        NameContainsKeywordsPredicate parentPredicate = new NameContainsKeywordsPredicate(parentPredicateMap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateMap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // different prefix (Name vs Parent Name) -> returns false
        assertFalse(firstPredicate.equals(parentPredicate));
    }

    @Test
    public void test_attributeContainsKeywords_returnsTrue() {
        // --- Student Name (Standard) ---
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Map.of(PREFIX_NAME, List.of("Alice")));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // --- Age ---
        predicate = new NameContainsKeywordsPredicate(Map.of(PREFIX_AGE, List.of("12")));
        assertTrue(predicate.test(new PersonBuilder().withAge("12").build()));

        // --- Dietary Remark ---
        predicate = new NameContainsKeywordsPredicate(Map.of(PREFIX_DIETARY_REMARK, List.of("Vegan")));
        assertTrue(predicate.test(new PersonBuilder().withDietaryRemark("Vegan").build()));

        // --- Tags ---
        predicate = new NameContainsKeywordsPredicate(Map.of(PREFIX_TAG, List.of("friends")));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends", "owesMoney").build()));

        // --- Parent Name ---
        predicate = new NameContainsKeywordsPredicate(Map.of(PREFIX_PARENT_NAME, List.of("Tan")));
        assertTrue(predicate.test(new PersonBuilder().withParentName("Tan Ah Teck").build()));
    }

    @Test
    public void test_attributeDoesNotContainKeywords_returnsFalse() {
        // Keyword matches address, but prefix is n/ (Name)
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Map.of(PREFIX_NAME, List.of("Main", "Street")));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withAddress("Main Street").build()));

        // Non-matching keyword for Age
        predicate = new NameContainsKeywordsPredicate(Map.of(PREFIX_AGE, List.of("15")));
        assertFalse(predicate.test(new PersonBuilder().withAge("12").build()));
    }

    @Test
    public void toStringMethod() {
        Map<Prefix, List<String>> keywordsMap = new HashMap<>();
        keywordsMap.put(PREFIX_NAME, List.of("keyword1"));
        keywordsMap.put(PREFIX_PARENT_NAME, List.of("keyword2"));
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywordsMap);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywordsMap=" + keywordsMap + "}";
        assertEquals(expected, predicate.toString());
    }
}

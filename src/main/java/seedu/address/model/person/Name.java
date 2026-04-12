package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain letters, numbers, spaces, hyphens, apostrophes and slashes (for particles like"
                    + " s/o), and it should not be blank";

    // First character must be a letter or number. Subsequent characters may include
    // Unicode letters (with diacritics), numbers, combining marks, spaces, hyphens,
    // apostrophes and slashes (e.g., s/o).
    public static final String VALIDATION_REGEX = "[\\p{L}\\p{N}][\\p{L}\\p{N}\\p{M} '\\-\\/]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a normalized version of the name where:
     * - all characters are converted to lowercase
     * - leading and trailing whitespace is removed
     * - multiple internal spaces are collapsed into a single space
     *
     * @return normalized name string
     */
    public String normalizeName() {
        return fullName.replaceAll("\\s+", " ").trim().toLowerCase();
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the age of a student.
 * <p>
 * An {@code Age} is stored as a string and must be a non-negative number
 * between 0 and 999 inclusive. Validation is performed using a regular
 * expression that ensures the value contains 1 to 3 digits.
 */
public class Age {

    public static final String MESSAGE_CONSTRAINTS =
            "Age must be a nonnegative number at least 1 digit long and no more than 3 digits long (0-999)";
    public static final String VALIDATION_REGEX = "\\d{1,3}";
    public final String value;

    /**
     * Constructs a {@code Age}.
     *
     * @param age Age of the student
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        value = age;
    }

    /**
     * Returns true if a given string is a valid age.
     */
    public static boolean isValidAge(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Age)) {
            return false;
        }

        Age otherAge = (Age) other;
        return value.equals(otherAge.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

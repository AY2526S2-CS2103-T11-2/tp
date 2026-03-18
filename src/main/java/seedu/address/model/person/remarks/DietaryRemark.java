package seedu.address.model.person.remarks;

/**
 * Represents a Person's dietary remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class DietaryRemark extends Remark {
    public final String value;

    /**
     * Constructs an {@code DietaryRemark}.
     *
     * @param dietaryRemark A valid dietary remark.
     */
    public DietaryRemark(String dietaryRemark) {
        super(dietaryRemark);
        value = dietaryRemark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DietaryRemark // instanceof handles nulls
                && value.equals(((DietaryRemark) other).value)); // state check
    }
}

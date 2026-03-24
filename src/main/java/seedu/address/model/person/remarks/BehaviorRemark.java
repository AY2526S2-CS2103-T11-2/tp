package seedu.address.model.person.remarks;

/**
 * Represents a Person's behavior remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class BehaviorRemark extends Remark {
    public final String value;

    /**
     * Constructs an {@code BehaviorRemark}.
     *
     * @param behaviorRemark A valid behavior remark.
     */
    public BehaviorRemark(String behaviorRemark) {
        super(behaviorRemark);
        value = behaviorRemark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BehaviorRemark // instanceof handles nulls
                && value.equals(((BehaviorRemark) other).value)); // state check
    }
}

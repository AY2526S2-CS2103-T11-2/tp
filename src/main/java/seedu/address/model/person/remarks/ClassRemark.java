package seedu.address.model.person.remarks;

/**
 * Represents a Person's class remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class ClassRemark extends Remark {
    public final String value;

    /**
     * Constructs an {@code ClassRemark}.
     *
     * @param classRemark A valid class remark.
     */
    public ClassRemark(String classRemark) {
        super(classRemark);
        value = classRemark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassRemark // instanceof handles nulls
                && value.equals(((ClassRemark) other).value)); // state check
    }
}

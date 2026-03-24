package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.remarks.BehaviorRemark;
import seedu.address.model.person.remarks.ClassRemark;
import seedu.address.model.person.remarks.DietaryRemark;
import seedu.address.model.person.remarks.Remark;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Age age;

    // Data fields
    private final Address address;
    private final Remark remark;
    private final DietaryRemark dietaryRemark;
    private final ClassRemark classRemark;
    private final BehaviorRemark behaviorRemark;
    private final Set<Tag> tags = new HashSet<>();

    // Parent fields
    private final Name parentName;
    private final Phone parentPhone;
    private final Email parentEmail;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Age age, Address address, Set<Tag> tags, Name parentName,
                  Phone parentPhone, Email parentEmail, Remark remark, DietaryRemark dietaryRemark,
                  ClassRemark classRemark, BehaviorRemark behaviorRemark) {
        requireAllNonNull(name, address, tags, parentName, parentPhone, parentEmail);
        this.name = name;
        this.age = age;
        this.address = address;
        this.remark = remark;
        this.dietaryRemark = dietaryRemark;
        this.classRemark = classRemark;
        this.behaviorRemark = behaviorRemark;
        this.tags.addAll(tags);
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
    }

    public Age getAge() {
        return age;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Name getParentName() {
        return parentName;
    }

    public Phone getParentPhone() {
        return parentPhone;
    }

    public Email getParentEmail() {
        return parentEmail;
    }

    public Remark getRemark() {
        return remark;
    }

    public DietaryRemark getDietaryRemark() {
        return dietaryRemark;
    }

    public ClassRemark getClassRemark() {
        return classRemark;
    }

    public BehaviorRemark getBehaviorRemark() {
        return behaviorRemark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && age.equals(otherPerson.age)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && parentName.equals(otherPerson.parentName)
                && parentPhone.equals(otherPerson.parentPhone)
                && parentEmail.equals(otherPerson.parentEmail);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, age, address, tags, parentName, parentPhone, parentEmail);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("age", age)
                .add("address", address)
                .add("tags", tags)
                .add("parentName", parentName)
                .add("parentPhone", parentPhone)
                .add("parentEmail", parentEmail)
                .toString();
    }

}

package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_AGE = "10";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_PARENT_NAME = "Parent Bee";
    public static final String DEFAULT_PARENT_PHONE = "98765432";
    public static final String DEFAULT_PARENT_EMAIL = "parent@gmail.com";

    private Name name;
    private Age age;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Name parentName;
    private Phone parentPhone;
    private Email parentEmail;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        age = new Age(DEFAULT_AGE);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        parentName = new Name(DEFAULT_PARENT_NAME);
        parentPhone = new Phone(DEFAULT_PARENT_PHONE);
        parentEmail = new Email(DEFAULT_PARENT_EMAIL);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        age = personToCopy.getAge();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        parentName = personToCopy.getParentName();
        parentPhone = personToCopy.getParentPhone();
        parentEmail = personToCopy.getParentEmail();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Age} of the {@code Person} that we are building.
     */
    public PersonBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code ParentName} of the {@code Person} that we are building.
     */
    public PersonBuilder withParentName(String parentName) {
        this.parentName = new Name(parentName);
        return this;
    }

    /**
     * Sets the {@code ParentPhone} of the {@code Person} that we are building.
     */
    public PersonBuilder withParentPhone(String parentPhone) {
        this.parentPhone = new Phone(parentPhone);
        return this;
    }

    /**
     * Sets the {@code ParentEmail} of the {@code Person} that we are building.
     */
    public PersonBuilder withParentEmail(String parentEmail) {
        this.parentEmail = new Email(parentEmail);
        return this;
    }

    public Person build() {
        return new Person(name, age, phone, email, address, tags, parentName, parentPhone, parentEmail);
    }

}

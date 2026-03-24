package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.remarks.BehaviorRemark;
import seedu.address.model.person.remarks.ClassRemark;
import seedu.address.model.person.remarks.DietaryRemark;
import seedu.address.model.person.remarks.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_AGE = "10";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_PARENT_NAME = "Parent Bee";
    public static final String DEFAULT_PARENT_PHONE = "98765432";
    public static final String DEFAULT_PARENT_EMAIL = "parent@gmail.com";
    public static final String DEFAULT_REMARK = "She likes aardvarks.";
    public static final String DEFAULT_DIETARYREMARK = "Fish";
    public static final String DEFAULT_CLASSREMARK = "1-A";
    public static final String DEFAULT_BEHAVIORREMARK = "Likes to scream";

    private Name name;
    private Age age;
    private Address address;
    private Remark remark;
    private DietaryRemark dietaryRemark;
    private ClassRemark classRemark;
    private BehaviorRemark behaviorRemark;
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
        address = new Address(DEFAULT_ADDRESS);
        remark = new Remark(DEFAULT_REMARK);
        dietaryRemark = new DietaryRemark(DEFAULT_DIETARYREMARK);
        classRemark = new ClassRemark(DEFAULT_CLASSREMARK);
        behaviorRemark = new BehaviorRemark(DEFAULT_BEHAVIORREMARK);
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
        address = personToCopy.getAddress();
        remark = personToCopy.getRemark();
        dietaryRemark = personToCopy.getDietaryRemark();
        classRemark = personToCopy.getClassRemark();
        behaviorRemark = personToCopy.getBehaviorRemark();
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

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code DietaryRemark} of the {@code Person} that we are building.
     */
    public PersonBuilder withDietaryRemark(String dietaryRemark) {
        this.dietaryRemark = new DietaryRemark(dietaryRemark);
        return this;
    }

    /**
     * Sets the {@code ClassRemark} of the {@code Person} that we are building.
     */
    public PersonBuilder withClassRemark(String classRemark) {
        this.classRemark = new ClassRemark(classRemark);
        return this;
    }

    /**
     * Sets the {@code BehaviorRemark} of the {@code Person} that we are building.
     */
    public PersonBuilder withBehaviorRemark(String behaviorRemark) {
        this.behaviorRemark = new BehaviorRemark(behaviorRemark);
        return this;
    }

    /**
     * Initializes the {@code Person} object with the current configuration of this builder.
     *
     * @return A new instance of {@code Person}.
     */
    public Person build() {
        return new Person(name, age, address, tags, parentName, parentPhone, parentEmail,
                remark, dietaryRemark, classRemark, behaviorRemark);
    }

}

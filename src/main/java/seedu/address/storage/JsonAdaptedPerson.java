package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
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

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String age;
    private final String address;
    private final String remark;
    private final String dietaryRemark;
    private final String classRemark;
    private final String behaviorRemark;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String parentName;
    private final String parentPhone;
    private final String parentEmail;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("age") String age,
                             @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("parentName") String parentName,
                             @JsonProperty("parentPhone") String parentPhone,
                             @JsonProperty("parentEmail") String parentEmail,
                             @JsonProperty("remark") String remark,
                             @JsonProperty("dietaryRemark") String dietaryRemark,
                             @JsonProperty("classRemark") String classRemark,
                             @JsonProperty("behaviorRemark") String behaviorRemark) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.remark = remark;
        this.dietaryRemark = dietaryRemark;
        this.classRemark = classRemark;
        this.behaviorRemark = behaviorRemark;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        age = source.getAge().value;
        address = source.getAddress().value;
        remark = source.getRemark().value;
        dietaryRemark = source.getDietaryRemark().value;
        classRemark = source.getClassRemark().value;
        behaviorRemark = source.getBehaviorRemark().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        parentName = source.getParentName().fullName;
        parentPhone = source.getParentPhone().value;
        parentEmail = source.getParentEmail().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
        }
        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_CONSTRAINTS);
        }
        final Age modelAge = new Age(age);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);

        if (dietaryRemark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DietaryRemark.class.getSimpleName()));
        }
        final DietaryRemark modelDietaryRemark = new DietaryRemark(dietaryRemark);

        if (classRemark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ClassRemark.class.getSimpleName()));
        }
        final ClassRemark modelClassRemark = new ClassRemark(classRemark);

        if (behaviorRemark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BehaviorRemark.class.getSimpleName()));
        }
        final BehaviorRemark modelBehaviorRemark = new BehaviorRemark(behaviorRemark);

        if (parentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(parentName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelParentName = new Name(parentName);

        if (parentPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(parentPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelParentPhone = new Phone(parentPhone);

        if (parentEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(parentEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelParentEmail = new Email(parentEmail);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelAge, modelAddress, modelTags, modelParentName, modelParentPhone,
                modelParentEmail, modelRemark, modelDietaryRemark, modelClassRemark, modelBehaviorRemark);
    }

}

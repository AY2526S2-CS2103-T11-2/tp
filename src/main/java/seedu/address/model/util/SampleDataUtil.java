package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");
    public static final DietaryRemark EMPTY_DIETARYREMARK = new DietaryRemark("");
    public static final ClassRemark EMPTY_CLASSREMARK = new ClassRemark("");
    public static final BehaviorRemark EMPTY_BEHAVIORREMARK = new BehaviorRemark("");

    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Age("11"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends"), new Name("Andre Yeoh"),
                    new Phone("98765432"), new Email("andreyeoh@gmail.com"), EMPTY_REMARK, EMPTY_DIETARYREMARK,
                    EMPTY_CLASSREMARK, EMPTY_BEHAVIORREMARK),
            new Person(new Name("Bernice Yu"), new Age("10"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"), new Name("Jacob Yu"),
                    new Phone("98765432"), new Email("jacobyu@gmail.com"), EMPTY_REMARK, EMPTY_DIETARYREMARK,
                    EMPTY_CLASSREMARK, EMPTY_BEHAVIORREMARK),
            new Person(new Name("Charlotte Oliveiro"), new Age("7"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"), new Name("charmaineoliveiro"),
                    new Phone("98765432"), new Email("charmaineoliveiro@gmail.com"), EMPTY_REMARK, EMPTY_DIETARYREMARK,
                    EMPTY_CLASSREMARK, EMPTY_BEHAVIORREMARK),
            new Person(new Name("David Li"), new Age("11"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family"), new Name("Timmy Li"),
                    new Phone("98765432"), new Email("timmyli@gmail.com"), EMPTY_REMARK, EMPTY_DIETARYREMARK,
                    EMPTY_CLASSREMARK, EMPTY_BEHAVIORREMARK),
            new Person(new Name("Irfan Ibrahim"), new Age("8"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates"), new Name("David Ibrahim"),
                    new Phone("98765432"), new Email("davidibrahim@gmail.com"), EMPTY_REMARK, EMPTY_DIETARYREMARK,
                    EMPTY_CLASSREMARK, EMPTY_BEHAVIORREMARK),
            new Person(new Name("Roy Balakrishnan"), new Age("7"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), new Name("Jacob Balakrishnan"),
                    new Phone("98765432"), new Email("jacobbalakrishnan@gmail.com"), EMPTY_REMARK, EMPTY_DIETARYREMARK,
                    EMPTY_CLASSREMARK, EMPTY_BEHAVIORREMARK)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

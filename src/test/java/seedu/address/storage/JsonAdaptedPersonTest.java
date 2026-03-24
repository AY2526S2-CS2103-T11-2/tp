package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_AGE = "23244";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_PARENT_NAME = "R@chel";
    private static final String INVALID_PARENT_PHONE = "+651234";
    private static final String INVALID_PARENT_EMAIL = "example.com";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_AGE = BENSON.getAge().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_REMARK = BENSON.getRemark().toString();
    private static final String VALID_DIETARY_REMARK = BENSON.getDietaryRemark().value;
    private static final String VALID_CLASS_REMARK = BENSON.getClassRemark().value;
    private static final String VALID_BEHAVIOR_REMARK = BENSON.getBehaviorRemark().value;
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_PARENT_NAME = BENSON.getName().toString();
    private static final String VALID_PARENT_PHONE = BENSON.getParentPhone().toString();
    private static final String VALID_PARENT_EMAIL = BENSON.getParentEmail().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_AGE, VALID_ADDRESS, VALID_TAGS,
                        VALID_PARENT_NAME, VALID_PARENT_PHONE, VALID_PARENT_EMAIL,
                        VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_AGE, VALID_ADDRESS, VALID_TAGS,
                VALID_PARENT_NAME, VALID_PARENT_PHONE, VALID_PARENT_EMAIL,
                VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_AGE, VALID_ADDRESS, VALID_TAGS,
                        VALID_PARENT_NAME, VALID_PARENT_PHONE, VALID_PARENT_EMAIL,
                        VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = Age.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_ADDRESS, VALID_TAGS,
                VALID_PARENT_NAME, VALID_PARENT_PHONE, VALID_PARENT_EMAIL,
                VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_AGE, INVALID_ADDRESS, VALID_TAGS,
                        VALID_PARENT_NAME, VALID_PARENT_PHONE, VALID_PARENT_EMAIL,
                        VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_AGE, null, VALID_TAGS,
                VALID_PARENT_NAME, VALID_PARENT_PHONE, VALID_PARENT_EMAIL,
                VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_AGE, VALID_ADDRESS, invalidTags,
                        VALID_PARENT_NAME, VALID_PARENT_PHONE, VALID_PARENT_EMAIL,
                        VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullParentName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_AGE, VALID_ADDRESS, VALID_TAGS,
                null, VALID_PARENT_PHONE, VALID_PARENT_EMAIL,
                VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidParentName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_AGE, VALID_ADDRESS, VALID_TAGS,
                        INVALID_PARENT_NAME, VALID_PARENT_PHONE, VALID_PARENT_EMAIL,
                        VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidParentPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_AGE, VALID_ADDRESS, VALID_TAGS,
                        VALID_PARENT_NAME, INVALID_PARENT_PHONE, VALID_PARENT_EMAIL,
                        VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullParentPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_AGE, VALID_ADDRESS, VALID_TAGS,
                        VALID_PARENT_NAME, null, VALID_PARENT_EMAIL,
                        VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullParentEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_AGE, VALID_ADDRESS, VALID_TAGS,
                        VALID_PARENT_NAME, VALID_PARENT_PHONE, null,
                        VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidParentEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_AGE, VALID_ADDRESS, VALID_TAGS,
                        VALID_PARENT_NAME, VALID_PARENT_PHONE, INVALID_PARENT_EMAIL,
                        VALID_REMARK, VALID_DIETARY_REMARK, VALID_CLASS_REMARK, VALID_BEHAVIOR_REMARK);
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}

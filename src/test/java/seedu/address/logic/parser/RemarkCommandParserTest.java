package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEHAVIOR_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIETARY_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.remarks.BehaviorRemark;
import seedu.address.model.person.remarks.ClassRemark;
import seedu.address.model.person.remarks.DietaryRemark;
import seedu.address.model.person.remarks.Remark;


public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();
    private final String nonEmptyRemark = "Some remark.";
    private final String nonEmptyDietaryRemark = "Some dietary remark.";
    private final String nonEmptyClassRemark = "Some class remark.";
    private final String nonEmptyBehaviorRemark = "Some behavior remark.";


    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK + nonEmptyRemark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(new Remark(nonEmptyRemark))));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(new Remark(""))));
        assertParseSuccess(parser, userInput, expectedCommand);

        // have dietary remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_DIETARY_REMARK + nonEmptyDietaryRemark;
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(new DietaryRemark(nonEmptyDietaryRemark))));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no dietary remark (simulating the delete action)
        userInput = targetIndex.getOneBased() + " " + PREFIX_DIETARY_REMARK;
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(new DietaryRemark(""))));
        assertParseSuccess(parser, userInput, expectedCommand);

        // have class remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_CLASS_REMARK + nonEmptyClassRemark;
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(new ClassRemark(nonEmptyClassRemark))));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no class remark (simulating the delete action)
        userInput = targetIndex.getOneBased() + " " + PREFIX_CLASS_REMARK;
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(new ClassRemark(""))));
        assertParseSuccess(parser, userInput, expectedCommand);

        // have behavior remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_BEHAVIOR_REMARK + nonEmptyBehaviorRemark;
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(new BehaviorRemark(nonEmptyBehaviorRemark))));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no behavior remark (simulating the delete action)
        userInput = targetIndex.getOneBased() + " " + PREFIX_BEHAVIOR_REMARK;
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(new BehaviorRemark(""))));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD + " " + nonEmptyRemark, expectedMessage);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.importer.CsvPersonImporter;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Imports persons from a CSV file into the address book.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports persons from a CSV file. "
            + "Parameters: FILE_PATH\n"
            + "CSV columns (fixed indices): "
            + "0=name,1=age,2=address,3=parentName,4=parentPhone,5=parentEmail,"
            + "6=tags,7=remark,8=dietaryRemark,9=classRemark,10=behaviorRemark\n"
            + "Leave optional fields empty in CSV if not needed (for example: ',,').\n"
            + "If present, tags should be separated by semicolons ';'.\n"
            + "Example: " + COMMAND_WORD + " data/contacts.csv";

    public static final String MESSAGE_FILE_READ_ERROR = "Unable to read CSV file: %1$s";
    public static final String MESSAGE_CSV_INVALID_FORMAT = "Invalid CSV format at line %1$d: %2$s";
    public static final String MESSAGE_SUCCESS = "Imported %1$d person(s). Skipped %2$d duplicate person(s).";

    private final Path csvFilePath;
    private final CsvPersonImporter csvPersonImporter;

    /**
     * @param csvFilePath path of the CSV file to import from
     */
    public ImportCommand(Path csvFilePath) {
        requireNonNull(csvFilePath);
        this.csvFilePath = csvFilePath;
        this.csvPersonImporter = new CsvPersonImporter();
    }

    /**
     * Executes the import command by reading persons from the configured CSV file
     * and adding non-duplicate persons to the model.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback containing imported and skipped duplicate counts
     * @throws CommandException if the CSV cannot be read or contains invalid data
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> importedPersons = csvPersonImporter.read(csvFilePath);
        int importedCount = 0;
        int skippedDuplicates = 0;

        for (Person person : importedPersons) {
            if (model.hasPerson(person)) {
                skippedDuplicates++;
                continue;
            }

            model.addPerson(person);
            importedCount++;
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, importedCount, skippedDuplicates));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return csvFilePath.equals(otherImportCommand.csvFilePath);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("csvFilePath", csvFilePath)
                .toString();
    }
}

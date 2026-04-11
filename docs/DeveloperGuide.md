---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# CareContacts Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is adapted from [AddressBook-Level3](https://se-education.org/addressbook-level3/).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### Import feature

#### Implementation

The import feature allows users to bulk-add students from a CSV file using the command `import FILE_PATH`.

At a high level, the command flow is:

1. `LogicManager` sends the user input to `AddressBookParser`.
1. `AddressBookParser` calls `ImportCommandParser` for `import` commands.
1. `ImportCommandParser` validates arguments, strips optional surrounding quotes, converts the path into `java.nio.file.Path`, and creates `ImportCommand`.
1. `ImportCommand#execute(Model)` calls `CsvPersonImporter#read(Path)` to parse the file into `Person` objects.
1. `ImportCommand` iterates through parsed persons:
   1. If `Model#hasPerson(person)` is true, the person is counted as a skipped duplicate.
   1. Otherwise, `Model#addPerson(person)` is called.
1. A `CommandResult` is returned with both imported and skipped duplicate counts.

The sequence diagram below summarizes this interaction:

<puml src="diagrams/ImportSequenceDiagram.puml" alt="ImportSequenceDiagram" />

`CsvPersonImporter` and `CsvPersonRowMapper` handle CSV-specific concerns:

* Rows are read in UTF-8.
* Empty lines are ignored.
* The first non-empty row is treated as a header and skipped if it matches
  `name,age,address,parentName,parentPhone,parentEmail` (case-insensitive and whitespace-insensitive).
* Quoted fields and escaped quotes (`""`) are supported.
* Each row must contain between 6 and 11 columns:
  * Required: `name`, `age`, `address`, `parentName`, `parentPhone`, `parentEmail`
  * Optional: `tags`, `remark`, `dietaryRemark`, `classRemark`, `behaviorRemark`
* Tags in the tags column are split by `;` and parsed as individual tags.

Error handling:

* If the file cannot be read, the command fails with a file-read error.
* If a CSV row is formatted incorrectly (e.g., unclosed quotes, invalid column count, or invalid field values), the command fails with the line number and validation reason.
* If duplicate persons are encountered, they are skipped (not treated as errors) and reported in the success message.

#### Design considerations

**Aspect: Duplicate handling policy**

* **Alternative 1 (current choice):** Skip duplicates and continue importing.
  * Pros: Better UX for bulk imports and idempotent repeated imports.
  * Cons: Users must check feedback to know exactly which rows were skipped.

* **Alternative 2:** Fail the entire import on first duplicate.
  * Pros: Strict behavior and clearer all-or-nothing semantics.
  * Cons: Less practical for real-world CSV files that may contain existing contacts.

**Aspect: CSV parsing strategy**

* **Alternative 1 (current choice):** Lightweight in-house parser (`CsvPersonImporter`).
  * Pros: No additional dependency and behavior is fully controlled.
  * Cons: Must maintain parser logic internally.

* **Alternative 2:** Use a third-party CSV library.
  * Pros: Mature parsing features and potentially fewer edge-case bugs.
  * Cons: Adds dependency and integration overhead for a relatively small feature.

### \[Proposed\] Data archiving

The data archiving feature allows users to move inactive or outdated students out of the main list into an archive, while still allowing them to be restored later if needed.

#### Implementation Overview

Data archiving is implemented by extending the `Model` to maintain two separate collections:
- an active student list
- an archived student list

Each student exists in exactly one of the following states:
- **Active**: visible in the main list and fully accessible
- **Archived**: hidden from the main list but retained in the system
- **Deleted**: permanently removed from the system

The transitions between these states are shown in the diagram below.

#### Command Flow

- `archive INDEX`  
  Moves a student from the active list to the archived list. The student is no longer shown in the main list.

- `restore INDEX`  
  Moves a student from the archived list back to the active list.

- `delete INDEX`  
  Removes a student permanently from the system, regardless of whether they are active or archived.

These operations are handled by the `Logic` component, which parses user input and invokes the appropriate updates in the `Model`.

#### Storage

The `Storage` component is extended to persist both active and archived students. This can be implemented by:
- storing both lists in a single JSON file under separate fields, or
- using a separate JSON file for archived students

This ensures that archived data is preserved across sessions.

#### Design Considerations

- **Clear state separation**: Distinguishing between active, archived, and deleted states reduces ambiguity and prevents unintended interactions with existing features.
- **Reversibility**: Archiving is designed to be reversible via the `restore` command, unlike deletion which is permanent.
- **Minimal disruption**: Existing features continue to operate only on active students, avoiding major changes to current logic.

<puml src="diagrams/DataArchivingState.puml" width="400" />

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**: Student care supervisor

* needs to manage a significant number of contacts
* can type fast
* prefers typing to mouse interactions
* prefers CLI to GUI

**Value proposition**: Provides a fast and reliable way to retrieve parents' contact details, edit and record student details with minimal effort. Optimized for users who are comfortable typing and prefers CLI to GUI


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                               | So that I can…​                                                          |
|----------|--------------------------------------------|------------------------------------------------------------|--------------------------------------------------------------------------|
| `* * *`  | user                                       | add student’s contact                                      | add their contact information if they are a new student                  |
| `* * *`  | user                                       | add a parent’s contact information under student’s contact | know which parent to contact for a specific student                      |
| `* * *`  | user                                       | delete a student’s contact                                 | remove their contact information if they are no longer part of the class |
| `* *`    | user                                       | import students from a CSV file                            | add many contacts quickly without repetitive manual entry                |
| `* * *`  | new user                                   | see how each command works by typing help                  | learn how to use the CLI                                                 |
| `* * *`  | user                                       | scroll to find a contact                                   | view the person’s contact information                                    |
| `* *`    | user                                       | search for a parent’s name or a student’s name             | find the contact if I only remember one of their names                   |
| `* *`    | user                                       | add dietary information about a student to a contact       | easily find the information via the address book                         |
| `* *`    | user                                       | sort contacts by age                                       | easily know if there are missing people from each batch                  |
| `* *`    | user                                       | mark the attendance of each student                        | keep track of who is absent during class                                 |
| `* *`    | user                                       | update a parent's contact information                      | make changes to their phone number/address                               |
| `* *`    | user                                       | mass delete contacts                                       | quickly remove multiple irrelevant contact information in one go         |
| `* *`    | user                                       | archive contact information                                | preserve contact information, while not being able to actively view it   |
| `* *`    | user                                       | see at a glance which students have allergies              | ensure they avoid unsafe foods                                           |
| `* *`    | user                                       | associate pictures with students                           | quickly recognize them                                                   |
| `* *`    | user                                       | mark which students have completed their homework          | keep track of which students have not completed their homework           |
| `* *`    | user                                       | access student behaviour notes                             | respond appropriately to behavioural issues                              |
| `* *`    | user                                       | group students by classroom                                | ensure only relevant teachers can access their information               |
| `* *`    | user                                       | group classrooms by year/batch                             | archive graduated students easily                                        |
| `* *`    | user                                       | associate a teacher with classroom(s)                      | grant them access to multiple student data at once                       |
| `*`      | user                                       | filter by classroom or year                                | show only relevant students in the list                                  |
| `*`      | user                                       | summarize the number of each dietary restrictions          | plan meals more easily                                                   |
| `*`      | user                                       | get notifications at dismissal times                       | dismiss students at the correct timings                                  |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `CareContacts` and the **Actor** is the `Student care supervisor`, unless specified otherwise)

**Use case: UC1 - Add a student**

**MSS**

1.  Student care supervisor requests to add a student with their information.
2.  CareContacts stores the student and the information related to the student.
3.  CareContacts displays a success message confirming the student has been stored.


    Use case ends.

**Extensions**

* 1a. CareContacts detects an invalid command format.

    * 1a1. CareContacts displays an error message.

      Use case ends.

* 1b. CareContacts detects a duplicate student name.

    * 1b1. CareContacts displays an error message.

      Use case ends.

**Use case: UC2 - Delete a student**

**MSS**

1.  Student care supervisor requests to delete a student's information.
2.  CareContacts deletes the student.
3.  CareContacts displays a deletion success message.


    Use case ends.

**Extensions**

* 1a. CareContacts detects an invalid command format.

    * 1a1. CareContacts displays an error message.

      Use case ends.

* 1b. The specified student does not exist.

    * 1b1. CareContacts displays an error message.

      Use case ends.

**Use case: UC3 - List all student information**

**MSS**

1.  Student care supervisor requests for all the stored students to be listed.
2.  CareContacts displays the list of all students added and all the information associated with each student.


    Use case ends.

**Use case: UC4 - Help command**

**MSS**


1. User enters the `help` command.
2. CareContacts displays a help window containing a subset of commonly used commands, their formats, and a link to the full User Guide.


    Use case ends.

**Use case: UC5 - Import students from a CSV file**

**MSS**

1.  Student care supervisor requests to import students with a CSV file path.
2.  CareContacts reads and validates the CSV file.
3.  CareContacts adds all valid non-duplicate students and skips duplicates.
4.  CareContacts displays a summary with imported and skipped duplicate counts.


    Use case ends.

**Extensions**

* 1a. CareContacts detects an invalid command format.

    * 1a1. CareContacts displays an error message.

      Use case ends.

* 1b. The specified CSV file cannot be read.

    * 1b1. CareContacts displays an error message.

      Use case ends.

* 2a. CareContacts detects an invalid CSV row format or invalid field value.

    * 2a1. CareContacts displays an error message with the CSV line number.

      Use case ends.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Data should be stored locally in a human editable text file.
3.  Data should persist across different sessions of the application.
4.  Software should work without requiring an installer.
5.  Should be able to hold up to 200 students without any noticeable decline in performance for typical usage.
6.  Should be usable by a user who has never used the software before.
7.  A user with above average typing speed for regular English text should be able to accomplish most of the tasks faster using commands than using the mouse.
8.  Should be able to work with or without internet access.
9.  Should be backward compatible with data produced by earlier versions of the software.


*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Student care center**: An afterschool program for primary-school aged children
* **Student care supervisor**: A staff member responsible for managing the daily operations and staff of the student care center
* **Student**: A child enrolled in the student care center whose information is stored in CareContacts

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Adding a student

1. Adding a student while students are being shown

   1. Prerequisites: No students with the same normalized name (case-insensitive, ignoring extra spaces) as "John Doe" exist in CareContacts.
   1. Test case: `add n/John Doe a/12 ad/311, Clementi Ave 2, #02-25 pn/Jane Doe pc/87516234 pe/janed@example.com t/friends t/basketball`
      Expected: The student is added to the list. Details of the added student are shown in the status message.

1. Adding a student whose normalized name already exists in CareContacts

   1. Prerequisites: A student named "Alex Yeoh" exists in CareContacts.
   1. Test case: `add n/alex  yeoh a/11 ad/289, Jurong West St 41, #03-12 pn/John Yeoh pc/91234567 pe/john.yeoh@example.com t/choir`
      Expected: A message warning the user of a possible duplicate is shown.
      Students with the same normalized name (case-insensitive, ignoring extra spaces), including the newly added student, are listed.
      The student is still added to CareContacts. Details of the added student are shown in the status message.

### Editing a student's details

1. Editing a student while students are being shown

    1. Test case: `edit 1 pc/12345678`
       Expected: The student at index 1's details are updated in the list to show the new parent contact details.
       Details of the edited student are shown in the status message.

1. Editing a student whose normalized name already exists in CareContacts

    1. Prerequisites: A student named "Alex Yeoh" exists in CareContacts. Another student exists in the CareContacts at index 1.

    1. Test case: `edit 1 n/alex      yeoh`
       Expected: A message warning the user of a possible duplicate is shown.
       Students with the same normalized name (case-insensitive, ignoring extra spaces), including the newly added student, are listed.
       The student name is still edited to CareContacts. Details of the edited student are shown in the status message.

      
### Deleting a student

1. Deleting a student while all students are being shown

   1. Prerequisites: List all students using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First student is deleted from the list. Details of the deleted student are shown in the status message.

   1. Test case: `delete 1 2 1 3`<br>
      Expected: Duplicated indices are handled (no duplicate deletions). The first, second, and third unique contacts are deleted from the list. Details of the deleted students are shown in the status message.
   1. Test case: `delete 1-3`<br>
      Expected: The first to third student in the list (inclusive) are deleted. Details of the deleted students are shown in the status message.

   1. Test case: `delete 0`<br>
      Expected: No student is deleted. Error details are shown in the status message.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Finding a student

1. Filtering by multiple attributes

    1. Prerequisites: Ensure `Alice Yeoh` (Age 11) and `Benson Meier` (Parent: `Sophia Meier`) are in the list.

    1. Test case: `find Alice pn/Sophia`<br>
    Expected: Both `Alice Yeoh` and `Benson Meier` are shown in the list.

    1. Test case: `find a/11`<br>
    Expected: `Alice Yeoh` is shown. (Note: `find a/1` should return no results if no one is exactly age 1).
   
    1. Test case: `find n/`<br>
    Expected: No search is performed. Error message for invalid command format is shown.

### Listing students

1. Listing and sorting contacts in a specified order

    1. Prerequisites: Ensure `Alice Yeoh` (Age 11), `Bernice Yu` (Age 10) and `Charlotte Oliveiro` (Age 7) are saved as contacts.

    1. Test case: `list n/`<br>
    Expected: Contacts are displayed in the following order (from top to bottom): `Alex Yeoh`, `Bernice Yu`, `Charlotte Oliveiro`.

    1. Test case: `list a/`<br>
    Expected: Contacts are displayed in the following order (from top to bottom): `Charlotte Oliveiro`, `Bernice Yu`, `Alex Yeoh`.

    1. Test case: `list` with any number of trailing whitespaces<br>
    Expected: Contacts are displayed with no sorting order applied.

    1. Test case: `list a`, `list 123`, `list .`
    Expected: View of contacts does not change. Error details are shown in the status message.

### Adding or removing remarks

1. Adding a general remark

   1. Prerequisites: List all persons using the `list` command. Ensure there is at least one person.

   1. Test case: `remark 1 r/Needs extra attention`<br>
      Expected: Person at index 1 is updated with the new remark. A success message is shown.

   1. Test case: `remark 1`<br>
      Expected: No changes are made. Error message for invalid command format is shown.

   1. Test case: `remark 0 r/test`<br>
      Expected: No changes are made. Error message for invalid command format is shown.

   1. Test case: `remark 999 r/test` (where 999 is out of range)<br>
      Expected: No changes are made. Error message indicates invalid person index.

1. Adding multiple remark fields in one command

   1. Prerequisites: List all persons using the `list` command.

   1. Test case: `remark 1 r/enjoys sports d/allergic to shellfish c/1C b/very energetic`<br>
      Expected: Person at index 1 has all specified fields updated. A success message is shown.

1. Removing an existing remark field

   1. Prerequisites: Ensure person at index 1 already has a dietary remark.

   1. Test case: `remark 1 d/`<br>
      Expected: Dietary remark for person at index 1 is removed. A success message is shown.

1. Attempting to remove an already empty field

   1. Prerequisites: Ensure person at index 1 has an empty remark field.

   1. Test case: `remark 1 r/`<br>
      Expected: No changes are made. Error message indicates the remark is already empty.

### Importing a CSV file

1. Importing a valid CSV file

   1. Prerequisites: Ensure `data/import-sample.csv` exists.

   1. Test case: `import data/import-sample.csv`<br>
      Expected: Students from the CSV file are added. A success message reports imported and skipped duplicate counts.

1. Importing a file path that contains spaces

   1. Prerequisites: Place a CSV file at `data/my contacts.csv`.

   1. Test case: `import "data/my contacts.csv"`<br>
      Expected: Import succeeds and displays the summary message.

1. Importing duplicates

   1. Prerequisites: Run `import data/import-sample.csv` once.

   1. Test case: Run `import data/import-sample.csv` again.<br>
      Expected: Existing students are skipped as duplicates and reflected in the summary.

1. Importing from a non-existent file

   1. Test case: `import data/does-not-exist.csv`<br>
      Expected: No students are added. An error message indicates the file cannot be read.

1. Importing a CSV with invalid data

   1. Prerequisites: Create a CSV file with an invalid value (e.g., invalid email in one row).

   1. Test case: `import data/invalid-data.csv`<br>
      Expected: No students are added. An error message indicates the failing CSV line and reason.

### Saving data

1. Dealing with missing/corrupted data files

   1. Test Case: Missing file
      1. Exit the application
      2. Delete `data/addressbook.json`
      3. Relaunch the application
      Expected: The application displays sample student entries. The file `data/addressbook.json` is automatically created if the user runs a command that modifies the data (e.g. `add` or `edit`). 
    
   1. Test Case: Corrupted `data/addressbook.json` file
      1. Exit the application
      2. Open the `data/addressbook.json` file
      3. Introduce invalid JSON or values
      4. Save the `data/addressbook.json` file
      5. Relaunch the application
      Expected: The application successfully launches, but does not display any entries. An error is 

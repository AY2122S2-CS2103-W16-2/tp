package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
/**
 * Finds, displays and copies the first instance of a person in address book whose name contains any
 * of the argument keywords to the system clipboard.
 * Keyword matching is case-insensitive.
 */
public class CopyCommand extends Command {


    public static final String COMMAND_WORD = "copy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Find the first person whose names contain any of "
            + "the specified keywords (case-insensitive) and copies and displays them.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_COPY_PERSON_SUCCESS = "Copied Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_EXIST = "The person with full name %1$s does not exist!";

    private final Name targetName;

    public CopyCommand(Name targetName) {
        this.targetName = targetName;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Optional<Person> personToCopyOptional = lastShownList
                .stream()
                .filter(person -> person.getName().equals(targetName))
                .findFirst();
        if (personToCopyOptional.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_EXIST, targetName));
        } else {
            Person personToCopy = personToCopyOptional.get();
            model.copyPerson(personToCopy);
            return new CommandResult(String.format(MESSAGE_COPY_PERSON_SUCCESS, personToCopy));
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyCommand // instanceof handles nulls
                && targetName.equals(((CopyCommand) other).targetName)); // state check
    }
}

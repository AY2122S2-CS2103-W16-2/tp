package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CopyCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CommandBox;


/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Pattern DYNAMIC_COMMAND_FORMAT = Pattern.compile("(?<inputType>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, CommandBox commandBox) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        final Matcher dynamicMatcher = DYNAMIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (dynamicMatcher.matches() && dynamicMatcher.group("inputType").equals("dynamic")) {
            final String arguments = dynamicMatcher.group("arguments").trim();
            String[] splitArgs = arguments.split(" ");

            if (isCommand(arguments, splitArgs)) {
                commandBox.setDynamicAsFalse();
                return new ListCommand();
            } else {
                commandBox.setDynamicAsTrue();
                return new FindCommandParser().parse(arguments);
            }
        } else {
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }

            final String commandWord = matcher.group("commandWord");
            final String arguments = matcher.group("arguments");

            switch (commandWord) {

            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);

            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case CopyCommand.COMMAND_WORD:
                return new CopyCommandParser().parse(arguments);

            case ListCommand.COMMAND_WORD:
                return new ListCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case ViewCommand.COMMAND_WORD:
                return new ViewCommandParser().parse(arguments);

            default:
                return new FindCommandParser().parse(commandWord);
            }
        }
    }

    /**
     * Checks if the user input matches a valid command
     *
     * @param command Raw user inputted command
     * @param splitCommand Array of user command separated by space
     * @return true if command is a valid command and false otherwise
     */
    public boolean isCommand(String command, String[] splitCommand) {
        return (command.equals("") || command.equals("add")
                || splitCommand[0].equals("add") || command.equals("find") || splitCommand[0].equals("find")
                || command.equals("edit") || splitCommand[0].equals("edit")) || command.equals("delete")
                || splitCommand[0].equals("delete");
    }
}

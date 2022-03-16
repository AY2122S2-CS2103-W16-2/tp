package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CopyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new CopyCommand object
 */
public class CopyCommandParser implements Parser<CopyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a CopyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CopyCommand parse(String args) throws ParseException {
        args = args.trim();
        try {
            Name name = ParserUtil.parseName(args);
            return new CopyCommand(name);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CopyCommand.MESSAGE_USAGE), pe);
        }
    }


}

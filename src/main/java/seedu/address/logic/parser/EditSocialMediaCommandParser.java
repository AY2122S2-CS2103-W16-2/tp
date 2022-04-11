package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLATFORM_NAME_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.edit.EditSocialMediaCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class EditSocialMediaCommandParser implements Parser<EditSocialMediaCommand> {

    private static final String MESSAGE_INVALID_SOCIAL_MEDIA_INDEX = "The social media index provided is invalid";
    @Override
    public EditSocialMediaCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput,
                PREFIX_SOCIAL_MEDIA, PREFIX_PLATFORM_NAME_FLAG, PREFIX_INDEX);

        Target target;

        try {
            target = ParserUtil.parseTarget(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSocialMediaCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.arePrefixesPresent(PREFIX_INDEX, PREFIX_SOCIAL_MEDIA)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSocialMediaCommand.MESSAGE_USAGE));
        }

        String newSocialMedia = argMultimap.getValue(PREFIX_SOCIAL_MEDIA).get();
        Index oneBasedIndex;
        try {
            oneBasedIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        } catch (ParseException e) {
            throw new ParseException(MESSAGE_INVALID_SOCIAL_MEDIA_INDEX);
        }

        if (argMultimap.doesPrefixesExist(PREFIX_PLATFORM_NAME_FLAG)) {
            return new EditSocialMediaCommand(target, oneBasedIndex, newSocialMedia, true);
        } else {
            return new EditSocialMediaCommand(target, oneBasedIndex, newSocialMedia, false);
        }

    }

}

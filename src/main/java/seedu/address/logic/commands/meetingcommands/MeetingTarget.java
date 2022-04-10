package seedu.address.logic.commands.meetingcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.Title;

/**
 * Encapsulates a indexed or named target on a given list of meetings, identified
 * either by their {@code Title} or {@code Index}.
 */
public class MeetingTarget {
    public static final String MESSAGE_MEETING_NOT_EXIST = "The meeting with full name %1$s does not exist!";

    private String target;

    /**
     * @param target the string to be treated as a target.
     * @throws ParseException if the string provided is not a valid index or title.
     *
     * <p>
     * Note that some non-negative integers (0, MAX_INT to INFINITY) are invalid indices, but are
     * valid names.
     * Thus, they will not be caught as invalid index during the instantiation of the Target class,
     * since they could very well represent a valid name at runtime when the target list is created.
     *
     * Thus, there is no choice but to defer the validation to runtime. If these values do not
     * represent a name in the list at runtime, then they need to be caught and treated as
     * invalid indices at runtime. </p>
     */
    public MeetingTarget(String target) throws ParseException {

        Optional<ParseException> indexParseExceptionOptional = tryParseIndex(target);
        Optional<ParseException> nameParseExceptionOptional = tryParseTitle(target);

        if (indexParseExceptionOptional.isPresent() && nameParseExceptionOptional.isPresent()
                && StringUtil.isInt(target)) {
            throw indexParseExceptionOptional.get();
        } else if (indexParseExceptionOptional.isPresent() && nameParseExceptionOptional.isPresent()) {
            throw nameParseExceptionOptional.get();
        }

        this.target = target;
    }

    /**
     * Overloaded constructor for Target
     * @param target the Index to be treated as a target.
     */
    public MeetingTarget(Index target) {
        this.target = String.valueOf(target.getOneBased());
    }

    /**
     * Overloaded constructor for Target
     * @param target the Name to be treated as a target.
     */
    public MeetingTarget(Title target) {
        this.target = target.toString();
    }

    /** Returns the {@code Person} that is targetted */
    public Meeting targetMeeting(List<Meeting> targetList) throws CommandException {
        assert targetList != null;

        Title targetTitle = parseTitle(target); //try name
        Optional<Meeting> targetByTitleOptional = targetList //find target person with said name
                .stream()
                .filter(meeting -> meeting.getTitle().equals(targetTitle))
                .findFirst();

        if (StringUtil.isInt(target) && targetByTitleOptional.isPresent()) { //check for target type confusion
            Optional<Meeting> targetByIndexOptional = getMeetingWithIndex(targetList, target);
            return targetByIndexOptional.orElse(targetByTitleOptional.get());

        } else if (StringUtil.isInt(target)) { //failed to target by name
            Index targetIndex;

            // catch invalid one-based indices caused by integer overflow or index 0,
            // both of which are valid names and will not be caught in the constructor of Target
            try {
                targetIndex = ParserUtil.parseIndex(target);
            } catch (ParseException p) {
                throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
            }

            if (targetIndex.getZeroBased() >= targetList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
            }
            Meeting targettedMeeting = targetList.get(targetIndex.getZeroBased());
            return targettedMeeting;

        } else {
            return targetByTitleOptional.orElseThrow(() ->
                    new CommandException(String.format(MESSAGE_MEETING_NOT_EXIST, targetTitle)));
        }
    }

    private Optional<Meeting> getMeetingWithIndex(List<Meeting> targetList, String idxString) {
        Index targetIndex;
        try {
            targetIndex = ParserUtil.parseIndex(target);
        } catch (ParseException e) {
            return Optional.empty();
        }

        if (targetIndex.getZeroBased() >= targetList.size()) {
            return Optional.empty();
        }

        Meeting targettedMeeting = targetList.get(targetIndex.getZeroBased());
        return Optional.of(targettedMeeting);
    }

    private Title parseTitle(String title) {
        requireNonNull(title);
        String trimmedName = title.trim();
        return new Title(trimmedName);
    }

    private Optional<ParseException> tryParseIndex(String target) {
        try {
            ParserUtil.parseIndex(target);
            return Optional.empty();
        } catch (ParseException p) {
            return Optional.of(p);
        }
    }

    private Optional<ParseException> tryParseTitle(String target) {
        try {
            ParserUtil.parseName(target);
            return Optional.empty();
        } catch (ParseException p) {
            return Optional.of(p);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MeetingTarget) {
            MeetingTarget otherTarget = (MeetingTarget) obj;
            return this.target.equals(otherTarget.target);
        }

        return false;
    }
}

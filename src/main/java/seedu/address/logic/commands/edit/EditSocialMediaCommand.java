package seedu.address.logic.commands.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLATFORM_NAME_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Target;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.socialmedia.PlatformDescription;
import seedu.address.model.socialmedia.PlatformName;
import seedu.address.model.socialmedia.SocialMedia;

public class EditSocialMediaCommand extends EditCommand {

    public static final String MESSAGE_EDIT_SOCIALS_SUCCESS = "Edited social media of %s: From %s to %s";
    public static final String MESSAGE_SOCIALS_ALREADY_EXISTS = "Socials %s already exists in %s!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the targeted social media of a given person. "
            + "The social media is specified by the index of list of socials of the given person.\n"
            + "Parameters: "
            + "PERSON_NAME/INDEX "
            + PREFIX_INDEX + "INDEX_NUM "
            + "[" + PREFIX_PLATFORM_NAME_FLAG + "] "
            + PREFIX_SOCIAL_MEDIA + "UPDATED_VALUE";

    private Target target;
    private Index index;
    private String newDetails;
    private boolean editPlatformNameflag;

    /**
     * @param target The target person to edit
     * @param index The index number of the socialMedia to edit
     * @param newDetails The new details of the socialMedia
     * @param editPlatformNameflag decides whether to edit the platform name or description
     */
    public EditSocialMediaCommand(Target target, Index index, String newDetails, boolean editPlatformNameflag) {

        this.target = target;
        this.index = index;
        this.newDetails = newDetails;
        this.editPlatformNameflag = editPlatformNameflag;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getSortedAndFilteredPersonList();

        Person targetPersonToEdit = target.targetPerson(lastShownList);
        List<SocialMedia> socialsToEdit = new ArrayList<>(targetPersonToEdit.getSocialMedias());
        SocialMedia socialMediaToEdit = socialsToEdit.get(index.getZeroBased());
        SocialMedia updatedSocialMedia;

        if (editPlatformNameflag) {
            updatedSocialMedia =
                    new SocialMedia(new PlatformName(newDetails), socialMediaToEdit.getPlatformDescription());
        } else {
            updatedSocialMedia =
                    new SocialMedia(socialMediaToEdit.getPlatformName(), new PlatformDescription(newDetails));
        }

        socialsToEdit.set(index.getZeroBased(), updatedSocialMedia);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        editPersonDescriptor.setSocials(socialsToEdit);

        Person updatedPerson = EditPersonDescriptor.createEditedPerson(targetPersonToEdit, editPersonDescriptor);
        model.setPerson(targetPersonToEdit, updatedPerson);
        model.updateSelectedPerson(updatedPerson);
        return new CommandResult(String.format(MESSAGE_EDIT_SOCIALS_SUCCESS,
                updatedPerson.getName(), socialMediaToEdit, updatedSocialMedia));
    }

}

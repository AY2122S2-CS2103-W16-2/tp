package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.socialmedia.SocialMedia;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified.\n"
            + "Specify the person to edit by their fullname or "
            + "by the index number used in the displayed person list.";

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Set<Tag> tags;
        private List<SocialMedia> socials;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            // setAddress(toCopy.address);
            setSocials(toCopy.socials);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, tags, socials);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setSocials(List<SocialMedia> socials) {
            this.socials = (socials != null) ? new ArrayList<>(socials) : null;
        }

        /**
         * Returns an unmodifiable social media list, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code socials} is null.
         */
        public Optional<List<SocialMedia>> getSocials() {
            return (socials != null) ? Optional.of(Collections.unmodifiableList(socials)) : Optional.empty();
        }

        /**
         * Creates and returns a {@code Person} with the details of {@code personToEdit}
         * edited with {@code editPersonDescriptor}.
         */
        public static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
            assert personToEdit != null;

            Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
            Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
            Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
            Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
            List<SocialMedia> updatedSocials = editPersonDescriptor.getSocials().orElse(personToEdit.getSocialMedias());

            return new Person(updatedName, updatedPhone, updatedEmail, updatedSocials, updatedTags);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getTags().equals(e.getTags())
                    && getSocials().equals(e.getSocials());
        }
    }
}

package seedu.address.model.socialmedia;

import java.util.Optional;

public class PlatformDescription {
    //TODO provide actual platform description constraints
    public static final String MESSAGE_CONSTRAINTS = "PLATFORM DESCRIPTION CONSTRAINTS!";
    private final String description;

    /**
     * Every field must be present and not null.
     */
    public PlatformDescription(String description) {
        //TODO note that empty string might cause parser to behave strangely...
        this.description = Optional.of(description).orElse("");
    }

    public String getValue() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PlatformDescription)) {
            return false;
        }

        PlatformDescription other = (PlatformDescription) obj;

        return this.description.equals(other.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public String toString() {
        return description;
    }

}
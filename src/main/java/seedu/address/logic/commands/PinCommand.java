package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Pin;
import seedu.address.model.tag.Tag;

/**
 * Pins a person identified using it's displayed index from the address book.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";
    public static final String COMMAND_DESCRIPTION = "Pins the person identified by the index number "
            + "used in the displayed person list.\n";

    public static final String COMMAND_EXAMPLE =
            "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_DESCRIPTION + COMMAND_EXAMPLE;

    public static final String MESSAGE_PINNED_PERSON_SUCCESS = "Pinned Person: %1$s";
    public static final String MESSAGE_PERSON_ALREADY_PINNED_FAILURE = "Person is already pinned!";

    private final Index targetIndex;

    public PinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToPin = lastShownList.get(targetIndex.getZeroBased());
        if (personToPin.isPinned()) {
            throw new CommandException(MESSAGE_PERSON_ALREADY_PINNED_FAILURE);
        }
        Person pinnedPerson = createPinnedPerson(personToPin);
        model.setPerson(personToPin, pinnedPerson);
        return new CommandResult(String.format(MESSAGE_PINNED_PERSON_SUCCESS, personToPin));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PinCommand // instanceof handles nulls
                && targetIndex.equals(((PinCommand) other).targetIndex)); // state check
    }

    private Person createPinnedPerson(Person personToPin) {
        requireNonNull(personToPin);
        Name updatedName = personToPin.getName();
        Phone updatedPhone = personToPin.getPhone();
        Email updatedEmail = personToPin.getEmail();
        Address updatedAddress = personToPin.getAddress();
        Set<Tag> updatedTags = personToPin.getTags();
        Birthday updatedBirthday = personToPin.getBirthday().orElse(null);
        assert !personToPin.isPinned() : "Person is already pinned!";
        Pin updatedPin = personToPin.getPin().togglePin();
        return new Person(updatedName, updatedPhone, updatedEmail,
                updatedAddress, updatedTags, updatedBirthday, updatedPin);
    }
}

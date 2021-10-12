package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a multiple persons identified using the displayed index range from the address book.
 */
public class DeleteMultipleCommand extends Command {
    public static final String COMMAND_WORD = "deletem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a range of persons identified by the index number used in the displayed person list.\n"
            + "Parameters: START_INDEX - END_INDEX (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 8 - 14";

    public static final String MESSAGE_DELETE_MULTIPLE_PERSON_SUCCESS = "Deleted Persons: ";
    public static final String MESSAGE_CONSTRAINTS = "STARTINDEX cannot be larger than ENDINDEX";

    private final Index startIndex;
    private final Index endIndex;

    /**
     * Deletes persons between a range of start index and end index.
     *
     * @param startIndex displayed index of first person to be deleted.
     * @param endIndex displayed index of last person to be deleted.
     */
    public DeleteMultipleCommand(Index startIndex, Index endIndex) {
        requireAllNonNull(startIndex, endIndex);
        checkArgument(areValidIndexes(startIndex, endIndex), MESSAGE_CONSTRAINTS);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    private Boolean areValidIndexes(Index startIndex, Index endIndex) {
        return startIndex.getZeroBased() <= endIndex.getZeroBased();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (startIndex.getZeroBased() >= lastShownList.size()
                || endIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        StringBuilder result = new StringBuilder(MESSAGE_DELETE_MULTIPLE_PERSON_SUCCESS);
        for (int i = startIndex.getZeroBased(); i <= endIndex.getZeroBased(); i++) {
            Person personToDelete = lastShownList.get(startIndex.getZeroBased());
            model.deletePerson(personToDelete);
            result.append(String.format("\n%1$s", personToDelete));
        }
        return new CommandResult(result.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && startIndex.equals(((DeleteMultipleCommand) other).startIndex)
                && endIndex.equals(((DeleteMultipleCommand) other).endIndex)); // state check
    }
}
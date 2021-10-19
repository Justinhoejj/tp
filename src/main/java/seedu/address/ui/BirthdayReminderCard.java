package seedu.address.ui;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;

public class BirthdayReminderCard extends UiPart<Region> {
    private static final String FXML = "BirthdayReminderListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label birthday;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public BirthdayReminderCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getFullName());
        phone.setText(person.getPhoneNumber());
        Optional<Birthday> possibleBirthday = person.getBirthday();
        assert possibleBirthday.isPresent();
        birthday.setText(possibleBirthday.map(Birthday::display).get());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return person.equals(card.person);
    }
}

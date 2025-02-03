package aurora.io;

import aurora.Aurora;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class Ui extends AnchorPane {

    // The singleton instance
    private static Ui singleton = null;
    private static boolean isSingletonSet = false;

    // The reference to Aurora application
    private Aurora aurora;

    // UI Elements
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Returns the singleton instance of Ui
     *
     * @return the singleton instance of Ui.
     */
    public static Ui getSingleton() {
        return singleton;
    }

    /**
     * Injects the Ui instance.
     *
     * @param ui The Ui instance to be injected.
     */
    public static void setUiSingleton(Ui ui) {
        if (isSingletonSet) {
            return;
        }
        singleton = ui;
        isSingletonSet = true;
    }

    /**
     * Initializes the Ui instance.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Aurora instance.
     *
     * @param aurora The Aurora instance to be injected.
     */
    public void setAurora(Aurora aurora) {
        this.aurora = aurora;
    }

    /**
     * Creates two dialog boxes, one echoing user input.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        aurora.executeInput(input); // does not wait for response in event of UI blocking
        userInput.clear();
    }

    /**
     * Displays the message in a dialog box.
     *
     * @param msg the message to be displayed.
     */
    public void printMsg(String msg) {
        dialogContainer.getChildren().addAll(
                DialogBox.getAuroraDialog(msg, dukeImage)
        );
    }

    /**
     * Closes Aurora after a delay.
     */
    public void close() {
        userInput.setDisable(true);
        sendButton.setDisable(true);
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> Platform.exit());
        delay.play();
    }

}

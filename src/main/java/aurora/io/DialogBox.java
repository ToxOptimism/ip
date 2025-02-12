package aurora.io;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructor for a dialog box.
     *
     * @param text the message in the dialog.
     * @param img the user profile image of the sender of the message.
     */
    private DialogBox(String text, Image img) {

        assert(text != null) : "Text is null.";
        assert(img != null) : "Image is null.";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Ui.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a user dialog box with the given text and image.
     *
     * @param text the message to be displayed in the dialog box.
     * @param img the user profile image to be displayed in the dialog box.
     * @return a user dialog box with the given text and image.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        assert(text != null) : "Text is null.";
        assert(img != null) : "Image is null.";

        return new DialogBox(text, img);
    }

    /**
     * Creates a chatbot dialog box with the given text and image.
     *
     * @param text the message to be displayed in the dialog box.
     * @param img the chatbot's profile image to be displayed in the dialog box.
     * @return a chatbot dialog box with the given text and image.
     */
    public static DialogBox getAuroraDialog(String text, Image img) {
        assert(text != null) : "Text is null.";
        assert(img != null) : "Image is null.";

        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}

package aurora;

import java.io.IOException;
import java.util.List;

import aurora.command.Command;
import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.Task;
import aurora.task.TaskList;
import aurora.util.Parser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Represents the main class of the Aurora application.
 */
public class Aurora extends Application {

    // Greeting string
    private static final String GREETING = "Hello! I'm Aurora.\nWhat can I do for you?";

    // The key components of the application
    private static final TaskList taskList = new TaskList();
    private static final Storage storage = Storage.of();
    private static Ui ui;

    /**
     * Loads the task list from the file.
     *
     * @throws AuroraException if there is an error loading the task list.
     */
    public static void loadTaskList() throws AuroraException {
        List<String> lines = Storage.of().loadTaskListData();
        List<Task> tasks = Parser.of().parseTaskListFile(lines);
        for (Task task : tasks) {

            assert(task != null) : "task is null.";
            taskList.addToList(task);
        }
    }

    /**
     * Acts as the main entry point of the Aurora application.
     *
     * @param stage The primary stage for this application, onto which the application scene can be set
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Aurora.class.getResource("/view/AuroraWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Aurora");
            fxmlLoader.<Ui>getController().setAurora(this); // inject the Duke instance
            Ui.setUiSingleton(fxmlLoader.getController()); // inject the Ui instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ui = Ui.getSingleton();

        try {
            storage.generateTaskListFile();
            loadTaskList();
        } catch (AuroraException e) {
            ui.printMsg(e.getMessage());
        }

        ui.printMsg(GREETING);

    }

    /**
     * Executes the user input.
     *
     * @param input The user input.
     */
    public void executeInput(String input) {
        try {
            Command command = Parser.of().parseCommand(input);
            command.execute(taskList, storage);
        } catch (AuroraException e) {
            ui.printMsg(e.getMessage());
        }
    }

}

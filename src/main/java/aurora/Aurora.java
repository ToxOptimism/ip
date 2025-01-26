package aurora;

import java.util.List;
import java.util.Scanner;

import aurora.command.Command;
import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.Task;
import aurora.task.TaskList;
import aurora.util.Parser;

/**
 * Represents the main class of the Aurora application.
 */
public class Aurora {

    // Greeting string
    private static final String GREETING = "Hello! I'm Aurora.\nWhat can I do for you?";

    // The key components of the application
    private static TaskList taskList = new TaskList();
    private static Storage storage = Storage.of();

    /**
     * Loads the task list from the file.
     *
     * @throws AuroraException if there is an error loading the task list.
     */
    public static void loadTaskList() throws AuroraException {
        List<String> lines = Storage.of().loadTaskListData();
        List<Task> tasks = Parser.of().parseTaskListFile(lines);
        for (Task task : tasks) {
            taskList.addToList(task);
        }
    }

    /**
     * The main entry point of the Aurora application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {

        try {
            storage.generateTaskListFile();
            loadTaskList();
        } catch (AuroraException e) {
            Ui.printMsg(e.getMessage());
            return;
        }

        Scanner sc = new Scanner(System.in);
        Command command = null;

        // Greet User
        Ui.printMsg(GREETING);

        // Chatbot
        while (command == null || !command.isExitCommand()) {
            String input = sc.nextLine().trim();
            try {
                command = Parser.of().parseCommand(input);
                command.execute(taskList, storage);
            } catch (AuroraException e) {
                Ui.printMsg(e.getMessage());
            }
        }

    }
}

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

public class Aurora {

    private static final String GREETING = "Hello! I'm Aurora.\nWhat can I do for you?";

    private static TaskList taskList = new TaskList();
    private static Storage storage = Storage.of();

    public static void loadTaskList() throws AuroraException {
        List<String> lines = Storage.of().loadTaskListData();
        List<Task> tasks = Parser.of().parseTaskListFile(lines);
        for (Task task : tasks) {
            taskList.addToList(task);
        }
    }

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

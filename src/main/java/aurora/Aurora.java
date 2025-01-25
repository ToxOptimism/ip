package aurora;

import java.util.Scanner;
import java.util.List;
import aurora.exception.AuroraException;
import aurora.io.Ui;
import aurora.io.Storage;
import aurora.task.Task;
import aurora.task.TaskList;
import aurora.command.Command;
import aurora.util.Parser;

public class Aurora {

    private static final String GREETING = "Hello! I'm Aurora.\nWhat can I do for you?";
    private static final String UNMARK = "This task has been marked as not done:";

    private static TaskList taskList = new TaskList();

    public static void loadTaskList() throws AuroraException {
        List<String> lines = Storage.loadTaskListData();
        List<Task> tasks = Parser.parseTaskListFile(lines);
        for (Task task : tasks) {
            taskList.addToList(task);
        }
    }

    public static void main(String[] args) {

        taskList = new TaskList();
        try {
            Storage.generateTaskListFile();
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
                command = Parser.parseCommand(input);
                command.execute(taskList);
            } catch (AuroraException e) {
                Ui.printMsg(e.getMessage());
            }
        }

    }
}

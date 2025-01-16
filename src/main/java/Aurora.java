import java.util.ArrayList;
import java.util.Scanner;

public class Aurora {

    private static final String GREETING = "Hello! I'm Aurora.\nWhat can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";
    private static final String MARK = "This task has been marked as done:";
    private static final String UNMARK = "This task has been marked as not done:";

    private static ArrayList<Task> taskList = new ArrayList<>();

    public static void printMsg(String msg) {
        System.out.println("=======================");
        System.out.println(msg);
        System.out.println("=======================");
    }

    public static void addList(String taskDescription) {
        Task t = new Task(taskDescription);
        taskList.add(t);
        printMsg("added: " + taskDescription);
    }

    public static void markTaskDone(int index) {
        if (index < 1 || index > taskList.size()) {
            return;
        }

        Task t = taskList.get(index - 1);
        t.markAsDone();

        printMsg(MARK + "\n" + t);
    }

    public static void unmarkTaskDone(int index) {
        if (index < 1 || index > taskList.size()) {
            return;
        }

        Task t = taskList.get(index - 1);
        t.unmarkAsDone();

        printMsg(UNMARK + "\n" + t);
    }

    public static void displayList() {
        int index = 1;

        System.out.println("=======================");
        for (Task task : taskList) {
            System.out.println(index + ". " + task);
        }
        System.out.println("=======================");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean end = false; // Boolean to determine if chatbot should end

        // Greet User
        printMsg(GREETING);

        // Chatbot
        while (!end) {
            String command = sc.nextLine();
            String[] argsList = command.split(" ");

            switch (argsList[0]) {
                case "bye":
                    end = true; // Break out of loop
                    break;
                case "list":
                    displayList();
                    break;
                case "mark":
                    markTaskDone(Integer.parseInt(argsList[1]));
                    break;
                case "unmark":
                    unmarkTaskDone(Integer.parseInt(argsList[1]));
                    break;
                default:
                    addList(command);
            }
        }

        // Say goodbye to user
        printMsg(GOODBYE);
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class Aurora {

    private static final String GREETING = "Hello! I'm Aurora.\nWhat can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";
    private static final String MARK = "This task has been marked as done:";
    private static final String UNMARK = "This task has been marked as not done:";
    private static final String ADD_TASK = "I've added this task: ";

    private static ArrayList<Task> taskList = new ArrayList<>();

    public static void printMsg(String msg) {
        System.out.println("=======================");
        System.out.println(msg);
        System.out.println("=======================");
    }

    public static void addToList(Task t) {
        taskList.add(t);
        printMsg(ADD_TASK + "\n" + t + "\n" + "Now you have " + taskList.size() + " tasks in the list!");
    }

    public static void addTask(String[] argsList) {
        if (argsList.length < 1) {
            return;
        }

        Task t = new Task(argsList[0]);
        addToList(t);
    }

    public static void addToDo(String[] argsList) {
        if (argsList.length < 2) {
            return;
        }
        ToDo td = new ToDo(argsList[1]);
        addToList(td);
    }

    public static void addDeadline(String[] argsList) {
        if (argsList.length < 2) {
            return;
        }

        String info = argsList[1];
        int byDateStart = info.indexOf("/by");

        if (byDateStart == -1) {
            return;
        }

        Deadline d = new Deadline(info.substring(0, byDateStart).trim(),
                info.substring(byDateStart + 3).trim());
        addToList(d);
    }

    public static void addEvent(String[] argsList) {
        if (argsList.length < 2) {
            return;
        }

        String info = argsList[1];
        int fromDateStart = info.indexOf("/from");
        int toDateStart = info.indexOf("/to");

        if (toDateStart == -1 || fromDateStart == -1 || fromDateStart > toDateStart) {
            return;
        }

        Event e = new Event(info.substring(0, fromDateStart).trim(),
                info.substring(fromDateStart + 5, toDateStart).trim(),
                info.substring(toDateStart + 3).trim());
        addToList(e);
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
            String command = sc.nextLine().trim();
            String[] argsList = command.split(" ", 2);

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
                case "todo":
                    addToDo(argsList);
                    break;
                case "deadline":
                    addDeadline(argsList);
                    break;
                case "event":
                    addEvent(argsList);
                    break;
                default:
                    addTask(argsList);
            }
        }

        // Say goodbye to user
        printMsg(GOODBYE);
    }
}

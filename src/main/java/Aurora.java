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

    public static void addToDo(String[] argsList) throws AuroraException {
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"todo Description\" ");
        }

        String info = argsList[1];

        // If there is no description provided
        if (info.trim().isEmpty()) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"todo Description\" ");
        }

        ToDo td = new ToDo(argsList[1]);
        addToList(td);
    }

    public static void addDeadline(String[] argsList) throws AuroraException{
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"deadline Description /by By\" ");
        }

        String info = argsList[1];
        String beforeBy = info.split("/by")[0].trim();

        int byDateStart = info.indexOf("/by");

        // If there is no description provided
        if (info.trim().isEmpty() || beforeBy.isEmpty()) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"deadline Description /by By\" ");
        }

        // If there is no /by
        if (byDateStart == -1) {
            throw new AuroraException("Missing argument: \"/by By\".\nUsage: \"deadline Description /by By\" ");

        // If there is no details after /to
        } else if (byDateStart + 3 == info.length()) {
            throw new AuroraException("Missing argument: \"By\" in \"/by By\".\nUsage: \"deadline Description /by By\" ");
        }

        Deadline d = new Deadline(beforeBy,
                info.substring(byDateStart + 3).trim());
        addToList(d);
    }

    public static void addEvent(String[] argsList) throws AuroraException {
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"event Description /from From /to To\" ");
        }

        String info = argsList[1];
        String beforeFrom = info.split("/from")[0].trim();
        String beforeTo = info.split("/to")[0].trim();

        int fromDateStart = info.indexOf("/from");
        int toDateStart = info.indexOf("/to");

        // If there is no description provided
        if (info.trim().isEmpty() || beforeFrom.isEmpty() || beforeTo.isEmpty()) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"event Description /from From /to To\" ");
        }

        // If there is no /from
        if (fromDateStart == -1) {
            throw new AuroraException("Missing argument: \"/from From\".\nUsage: \"event Description /from From /to To\" ");

        // If there is no /to
        } else if (toDateStart == -1) {
            throw new AuroraException("Missing argument: \"/to To\".\nUsage: \"event Description /from From /to To\" ");

        // If format is in wrong order
        } else if (fromDateStart > toDateStart) {
            throw new AuroraException("Invalid format: \"/from From\" must be before \"/to To\".\nUsage: \"event Description /from From /to To\" ");

        // If there is no details after /from
        } else if (fromDateStart + 5 == beforeTo.length()) {
            throw new AuroraException("Missing argument: \"From\" in \"/from From\".\nUsage: \"event Description /from From /to To\" ");

        // If there is no details after /to
        } else if (toDateStart + 3 == info.length()) {
            throw new AuroraException("Missing argument: \"To\" in \"/to To\".\nUsage: \"event Description /from From /to To\" ");
        }

        Event e = new Event(info.substring(0, fromDateStart).trim(),
                info.substring(fromDateStart + 5, toDateStart).trim(),
                info.substring(toDateStart + 3).trim());
        addToList(e);
    }

    public static void checkWithinTaskList(int index, int lower, int upper) throws AuroraException {
        if (lower > upper) {
            throw new AuroraException("Task List is empty. Unable to run command.");
        } else if (index < lower || index > upper) {
            throw new AuroraException("Argument provided \"" + index + "\" must be between bounds of " + lower + " and " + upper + ".");
        }
    }

    public static void markTaskDone(int index) throws AuroraException {
        checkWithinTaskList(index, 1, taskList.size()); // throws an exception

        Task t = taskList.get(index - 1);
        t.markAsDone();

        printMsg(MARK + "\n" + t);
    }

    public static void unmarkTaskDone(int index) throws AuroraException{
        checkWithinTaskList(index, 1, taskList.size()); // throws an exception

        Task t = taskList.get(index - 1);
        t.unmarkAsDone();

        printMsg(UNMARK + "\n" + t);
    }

    public static void displayList() {
        int index = 1;

        System.out.println("=======================");
        for (Task task : taskList) {
            System.out.println(index + ". " + task);
            index += 1;
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
            try {
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
                        throw new AuroraException("Unknown command: " + command);
                }
            } catch (AuroraException e) {
                printMsg(e.getMessage());
            }
        }

        // Say goodbye to user
        printMsg(GOODBYE);
    }
}

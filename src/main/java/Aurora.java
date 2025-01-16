import java.util.ArrayList;
import java.util.Scanner;

public class Aurora {

    private static final String GREETING = "Hello! I'm Aurora.\nWhat can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";
    private static ArrayList<String> taskList = new ArrayList<>();

    public static void printMsg(String msg) {
        System.out.println("=======================");
        System.out.println(msg);
        System.out.println("=======================");
    }

    public static void addList(String task) {
        taskList.add(task);
        printMsg("added: " + task);
    }

    public static void displayList() {
        int index = 1;

        System.out.println("=======================");
        for (String task : taskList) {
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
                default:
                    addList(command);
            }
        }

        // Say goodbye to user
        printMsg(GOODBYE);
    }
}

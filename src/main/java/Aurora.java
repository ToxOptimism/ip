import java.util.Scanner;

public class Aurora {

    private static final String GREETING = "Hello! I'm Aurora.\nWhat can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    public static void printMsg(String msg) {
        System.out.println("=======================");
        System.out.println(msg);
        System.out.println("=======================");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean end = false; // Boolean to determine if chatbot should end

        // Greet User
        printMsg(GREETING);

        // Chatbot
        while (!end) {
            String[] command = sc.nextLine().split(" ");

            switch (command[0]) {
                case "bye":
                    end = true; // Break out of loop
                    break;
                default:
                    printMsg(command[0]);
            }
        }

        // Say goodbye to user
        printMsg(GOODBYE);
    }
}

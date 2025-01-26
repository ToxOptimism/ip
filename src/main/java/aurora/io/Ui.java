package aurora.io;

import aurora.task.TaskList;

/**
 * Represents the user interface of the chatbot
 */
public class Ui {

    /**
     * Prints the message with the appropriate format.
     *
     * @param msg the message to be printed.
     */
    public static void printMsg(String msg) {
        System.out.println("=======================");
        System.out.println(msg);
        System.out.println("=======================");
    }

    /**
     * Displays the list of tasks.
     *
     * @param taskList the list of tasks to be displayed.
     */
    public static void displayList(TaskList taskList) {

        System.out.println("=======================");
        if (taskList.getSize() != 0) {
            System.out.println(taskList.toString());
        }
        System.out.println("=======================");
    }
}

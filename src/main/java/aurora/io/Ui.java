package aurora.io;

import aurora.task.TaskList;

public class Ui {
    public static void printMsg(String msg) {
        System.out.println("=======================");
        System.out.println(msg);
        System.out.println("=======================");
    }

    public static void displayList(TaskList taskList) {

        System.out.println("=======================");
        if (taskList.getSize() != 0) {
            System.out.println(taskList);
        }
        System.out.println("=======================");
    }
}

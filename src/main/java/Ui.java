public class Ui {
    public static void printMsg(String msg) {
        System.out.println("=======================");
        System.out.println(msg);
        System.out.println("=======================");
    }

    public static void displayList(TaskList taskList) {

        System.out.println("=======================");
        if (taskList.getSize() != 0) {
            System.out.println(taskList.toString());
        }
        System.out.println("=======================");
    }
}

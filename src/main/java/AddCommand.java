import java.util.ArrayList;
import java.util.List;

abstract public class AddCommand extends Command {

    public void addToList(Task t, TaskList taskList) throws AuroraException {
        taskList.addToList(t);
        Ui.printMsg("I've added this task:" + "\n" + t + "\n" + "Now you have " + taskList.getSize() + " tasks in the list!");
        appendTaskListFile(t);
    }

}

package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.Task;
import aurora.task.TaskList;

abstract public class AddCommand extends Command {

    public void addToList(Task t, TaskList taskList, Storage storage) throws AuroraException {
        taskList.addToList(t);
        Ui.printMsg("I've added this task:" + "\n" + t + "\n" + "Now you have " + taskList.getSize() + " tasks in the list!");
        appendTaskListFile(t, storage);
    }

}

package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.Task;
import aurora.task.TaskList;

/**
 * Represents an add command.
 */
abstract public class AddCommand extends Command {

    /**
     * Adds a task to the task list and prints a success message.
     *
     * @param t the task to be added.
     * @param taskList the taskList that stores the task.
     * @param storage the storage to write to.
     * @throws AuroraException if appending to storage fails.
     */
    public void addToList(Task t, TaskList taskList, Storage storage) throws AuroraException {
        taskList.addToList(t);
        Ui.printMsg("I've added this task:" + "\n" + t + "\n" + "Now you have " + taskList.getSize() + " tasks in the list!");
        appendTaskListFile(t, storage);
    }

}

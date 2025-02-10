package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.Task;
import aurora.task.TaskList;

/**
 * Represents an add command.
 */
public abstract class AddCommand extends Command {

    /**
     * Adds a task to the task list and prints a success message.
     *
     * @param task the task to be added.
     * @param taskList the taskList that stores the task.
     * @param storage the storage to write to.
     * @throws AuroraException if appending to storage fails.
     */
    public void addToList(Task task, TaskList taskList, Storage storage) throws AuroraException {
        taskList.addToList(task);
        Ui.getSingleton().printMsg("I've added this task:" + "\n" + task + "\n" + "Now you have " + taskList.getSize()
                + " tasks in the list!");
        appendTaskListFile(task, storage);
    }

}

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

    private static final String TASK_ADDED_MSG = "I've added this task:%n%s%nNow you have %d tasks in the list!";

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
        String message = String.format(TASK_ADDED_MSG, task, taskList.getSize());
        Ui.getSingleton().printMsg(message);
        appendTaskListFile(task, storage);
    }

}

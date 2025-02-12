package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.TaskList;

/**
 * Represents a command to display the taskList.
 */
public class ListCommand extends Command {

    /**
     * Executes the command to display the taskList.
     *
     * @param taskList the taskList to display.
     * @param storage the storage for referencing.
     * @throws AuroraException if an error occurs in lower-level method.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        assert(taskList != null) : "The taskList is null.";
        assert(storage != null) : "Storage is null.";

        super.execute(taskList, storage);

        if (taskList.getSize() != 0) {
            Ui.getSingleton().printMsg(taskList.toString());
        } else {
            Ui.getSingleton().printMsg("The list is empty.");
        }
    }


}

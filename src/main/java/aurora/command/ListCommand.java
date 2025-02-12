package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.TaskList;

/**
 * Represents a command to display the taskList.
 */
public class ListCommand extends Command {

    public static final String CMD_KEYWORD = "list";

    private static final String EMPTY_LIST = "The list is empty.";

    /**
     * Executes the command to display the taskList.
     *
     * @param taskList the taskList to display.
     * @param storage the storage for referencing.
     * @throws AuroraException if an error occurs in lower-level method.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        super.execute(taskList, storage);

        Ui ui = Ui.getSingleton();

        if (taskList.getSize() == 0) {
            ui.printMsg(EMPTY_LIST);
            return;
        }

        ui.printMsg(taskList.toString());
    }


}

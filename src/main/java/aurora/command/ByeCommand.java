package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.TaskList;

/**
 * Represents a command to exit the chatbot.
 */
public class ByeCommand extends Command {

    /**
     * Indicates whether the command will exit the program.
     *
     * @return true.
     */
    @Override
    public boolean isExitCommand() {
        return true;
    }

    /**
     * Executes the command to print a goodbye message.
     *
     * @param taskList the taskList for referencing.
     * @param storage the storage for referencing.
     * @throws AuroraException if an error occurs in lower-level method.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        super.execute(taskList, storage);

        // Say goodbye to user
        Ui.printMsg("Bye. Hope to see you again soon!");

    }


}

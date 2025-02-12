package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.TaskList;

/**
 * Represents a command to exit the chatbot.
 */
public class ByeCommand extends Command {

    public static final String CMD_KEYWORD = "bye";

    public static final String BYE_MESSAGE = "Bye. Hope to see you again soon!";

    /**
     * Executes the command to print a goodbye message.
     *
     * @param taskList the taskList for referencing.
     * @param storage the storage for referencing.
     * @throws AuroraException if an error occurs in lower-level method.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        assert(taskList != null) : "The taskList is null.";
        assert(storage != null) : "Storage is null.";

        super.execute(taskList, storage);

        // Say goodbye to user
        Ui ui = Ui.getSingleton();
        ui.printMsg(BYE_MESSAGE);
        ui.close();

    }


}

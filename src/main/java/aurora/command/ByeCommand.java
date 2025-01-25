package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.TaskList;

public class ByeCommand extends Command {

    @Override
    public boolean isExitCommand() {
        return true;
    }

    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        super.execute(taskList, storage);

        // Say goodbye to user
        Ui.printMsg("Bye. Hope to see you again soon!");

    }


}

package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Ui;
import aurora.task.TaskList;

public class ByeCommand extends Command {

    @Override
    public boolean isExitCommand() {
        return true;
    }

    @Override
    public void execute(TaskList taskList) throws AuroraException {

        super.execute(taskList);

        // Say goodbye to user
        Ui.printMsg("Bye. Hope to see you again soon!");

    }


}

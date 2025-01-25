package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Ui;
import aurora.task.TaskList;

public class ListCommand extends Command {

    @Override
    public void execute(TaskList taskList) throws AuroraException {

        super.execute(taskList);

        Ui.displayList(taskList);

    }


}

package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.TaskList;

public class ListCommand extends Command {

    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        super.execute(taskList, storage);

        Ui.displayList(taskList);

    }


}

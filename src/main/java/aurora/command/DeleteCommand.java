package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.Task;
import aurora.task.TaskList;
import aurora.util.Parser;

public class DeleteCommand extends Command {

    private int index;

    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        super.execute(taskList, storage);

        Task t = taskList.deleteFromList(index);

        Ui.printMsg("I've removed this task:"+ "\n" + t + "\n" + "Now you have " + taskList.getSize() + " tasks in the list!");
        overwriteTaskListFile(taskList, storage);
    }

    @Override
    public void parseArgs(String[] argsList) throws AuroraException {
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"delete Index\"");

        // Argument provided is not an integer
        } else if (!Parser.of().canParseInt(argsList[1])) {
            throw new AuroraException("Invalid arguments: index must be a valid integer value.\nUsage: \"delete Index\"");
        }

        index = Integer.parseInt(argsList[1]);

        super.parseArgs(argsList);
    }

}

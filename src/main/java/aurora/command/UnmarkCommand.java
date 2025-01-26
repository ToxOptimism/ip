package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.Task;
import aurora.task.TaskList;
import aurora.util.Parser;

/**
 * Represents a command to mark a task as not done from the TaskList at a specified index.
 */
public class UnmarkCommand extends Command {

    // The index to the task to unmark is at
    private int index;

    /**
     * Executes the command to unmark a task at a specified index.
     *
     * @param taskList the taskList that the task to unmark is within.
     * @param storage the storage to overwrite with taskList data.
     * @throws AuroraException if an error occurs in lower-level method.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        super.execute(taskList, storage);
        Task t = taskList.unmarkTaskDone(index); // throws AuroraException if index is out of bounds

        Ui.printMsg("This task has been marked as not done:" + "\n" + t);
        overwriteTaskListFile(taskList, storage);
    }

    /**
     * Parses the arguments for the command.
     *
     * @param argsList the arguments to parse.
     * @throws AuroraException the appropriate exception message if unable to parse arguments.
     */
    @Override
    public void parseArgs(String[] argsList) throws AuroraException {
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: Index must be a valid integer value.\n"
                    + "Usage: \"unmark Index\"");

        // Argument provided is not an integer
        } else if (!Parser.of().canParseInt(argsList[1])) {
            throw new AuroraException("Invalid arguments: Index must be a valid integer value.\n"
                    + "Usage: \"unmark Index\"");
        }

        index = Integer.parseInt(argsList[1]);
        super.parseArgs(argsList);
    }

}

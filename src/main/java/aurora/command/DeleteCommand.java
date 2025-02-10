package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.Task;
import aurora.task.TaskList;
import aurora.util.Parser;

/**
 * Represents a command to delete a task from the TaskList at a specified index.
 */
public class DeleteCommand extends Command {

    public static final String CMD_KEYWORD = "delete";

    private static final String USAGE = "Usage: \"delete Index\"";
    private static final String TASK_REMOVED_MSG = "I've remove this task:%n%s%nNow you have %d tasks in the list!";

    // Exception messages
    private static final String MISSING_INDEX =
            "Missing argument: \"Index\".";
    private static final String INVALID_INDEX_ARG =
            "Invalid arguments: index must be a valid integer value.";

    // The index to the task to delete is at
    private int index;

    /**
     * Executes the command to delete a task at a specified index.
     *
     * @param taskList the taskList to delete from.
     * @param storage the storage to overwrite with taskList data.
     * @throws AuroraException if an error occurs in lower-level method.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        super.execute(taskList, storage);

        Task task = taskList.deleteFromList(index); // throws AuroraException if index is out of bounds

        String message = String.format(TASK_REMOVED_MSG, task, taskList.getSize());
        Ui.getSingleton().printMsg(message);
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
            throw new AuroraException(MISSING_INDEX + "\n" + USAGE);

        // Argument provided is not an integer
        } else if (!Parser.of().canParseInt(argsList[1])) {
            throw new AuroraException(INVALID_INDEX_ARG + "\n" + USAGE);
        }

        index = Integer.parseInt(argsList[1]);

        super.parseArgs(argsList);
    }

}

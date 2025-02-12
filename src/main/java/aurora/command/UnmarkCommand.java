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

    public static final String CMD_KEYWORD = "unmark";

    private static final String USAGE = "Usage: \"unmark Index\"";
    private static final String TASK_UNMARKED_MSG = "This task has been marked as done:%n%s";

    // Exception messages
    private static final String MISSING_INDEX =
            "Missing argument: \"Index\".";
    private static final String INVALID_INDEX_ARG =
            "Invalid arguments: index must be a valid integer value.";

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
        Task task = taskList.unmarkTaskDone(index); // throws AuroraException if index is out of bounds

        String message = String.format(TASK_UNMARKED_MSG, task);
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
        /*
         * The code may seem to be duplicated as a number of commands may share similar parsing.
         * However, the code is designed with the fact that the parsing of arguments is meant to be
         * coupled with the command it is parsing for, for ease of extending the code.
         */

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

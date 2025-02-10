package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.task.TaskList;
import aurora.task.ToDo;

/**
 * Represents a command to add a ToDo to the TaskList.
 */
public class AddToDoCommand extends AddCommand {

    public static final String CMD_KEYWORD = "todo";

    private static final String USAGE = "Usage: \"todo Description\"";

    // Exception messages
    private static final String MISSING_DESCRIPTION =
            "Missing argument: \"Description\".";

    // ToDo specific fields
    private String description;

    /**
     * Executes the command to add a ToDo to the TaskList.
     *
     * @param taskList the taskList to add to.
     * @param storage the storage to write to.
     * @throws AuroraException if an error occurs in lower-level method.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        super.execute(taskList, storage);

        ToDo toDo = new ToDo(description);
        addToList(toDo, taskList, storage);

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
            throw new AuroraException(MISSING_DESCRIPTION + "\n" + USAGE);
        }

        description = argsList[1].trim();

        // If there is no description provided
        if (description.isEmpty()) {
            throw new AuroraException(MISSING_DESCRIPTION + "\n" + USAGE);
        }

        super.parseArgs(argsList);
    }

}

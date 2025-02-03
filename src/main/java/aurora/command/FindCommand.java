package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.TaskList;

/**
 * Represents a command to find tasks that match a keyword.
 */
public class FindCommand extends Command {

    // FindCommand specific fields
    private String keyword;

    /**
     * Executes the command to find tasks that match the keyword.
     *
     * @param taskList the TaskList to add to.
     * @param storage the storage for referencing.
     * @throws AuroraException if an error occurs in a lower-level method.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        super.execute(taskList, storage);

        TaskList filteredList = taskList.findMatchingKeyword(keyword);
        if (taskList.getSize() != 0) {
            Ui.getSingleton().printMsg(taskList.toString());
        } else {
            Ui.getSingleton().printMsg("The list is empty.");
        }
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
            throw new AuroraException("Missing argument: \"Keyword\".\n"
                    + "Usage: \"find Keyword\"");
        }

        keyword = argsList[1].trim();

        // If there is no description provided
        if (keyword.isEmpty()) {
            throw new AuroraException("Missing argument: \"Keyword\".\n"
                    + "Usage: \"find Keyword\"");
        }

        super.parseArgs(argsList);
    }

}

package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.Ui;
import aurora.task.TaskList;

/**
 * Represents a command to find tasks that match a keyword.
 */
public class FindCommand extends Command {

    public static final String CMD_KEYWORD = "find";

    private static final String USAGE = "Usage: \"find Keyword\"";
    private static final String EMPTY_LIST = "The list is empty.";

    // Exception messages
    private static final String MISSING_KEYWORD_ARG =
            "Missing argument: \"Keyword\".";

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

        Ui ui = Ui.getSingleton();
        TaskList filteredList = taskList.findMatchingKeyword(keyword);
        if (filteredList.getSize() != 0) {
            ui.printMsg(filteredList.toString());
        } else {
            ui.printMsg(EMPTY_LIST);
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
            throw new AuroraException(MISSING_KEYWORD_ARG + "\n" + USAGE);
        }

        keyword = argsList[1].trim();

        // If there is no description provided
        if (keyword.isEmpty()) {
            throw new AuroraException(MISSING_KEYWORD_ARG + "\n" + USAGE);
        }

        super.parseArgs(argsList);
    }

}

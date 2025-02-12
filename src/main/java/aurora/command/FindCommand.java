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

        assert(taskList != null) : "The taskList is null.";
        assert(storage != null) : "Storage is null.";

        super.execute(taskList, storage);

        Ui ui = Ui.getSingleton();
        TaskList filteredList = taskList.findMatchingKeyword(keyword);

        if (filteredList.getSize() == 0) {
            ui.printMsg(EMPTY_LIST);
            return;
        }

        ui.printMsg(filteredList.toString());
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

        assert(argsList != null) : "The argsList is null.";

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

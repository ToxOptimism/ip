package aurora.command;

import java.time.LocalDateTime;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.task.Deadline;
import aurora.task.TaskList;
import aurora.util.Parser;

/**
 * Represents a command to add a Deadline to the TaskList.
 */
public class AddDeadlineCommand extends AddCommand {

    public static final String CMD_KEYWORD = "deadline";

    private static final String BY_ARG_IDENTIFIER = "/by";
    private static final String USAGE = "Usage: \"deadline Description /by By\"";

    // Exception messages
    private static final String MISSING_DESCRIPTION_ARG =
            "Missing argument: \"Description\".";
    private static final String MISSING_BY_ARG_IDENTIFIER =
            "Missing argument: \"/by By\".";
    private static final String MISSING_BY_ARG =
            "Missing argument: \"By\" in \"/by By\".";
    private static final String INVALID_BY_DATE_ARG =
            "Invalid format: \"By\" must be a valid date format of dd/mm/yyyy hhmm.";

    // Deadline specific fields
    private LocalDateTime byDate;
    private String description;

    /**
     * Executes the command to add a Deadline to the TaskList.
     *
     * @param taskList the taskList to add to.
     * @param storage the storage to write to.
     * @throws AuroraException if an error occurs in lower-level method.
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        assert(taskList != null) : "The taskList is null.";
        assert(storage != null) : "Storage is null.";

        super.execute(taskList, storage);

        Deadline deadline = new Deadline(description, byDate);
        addToList(deadline, taskList, storage);
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
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        String argument = argsList[1];

        // If argument is trailing white space
        if (argument.trim().isEmpty()) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        int byDateStartIndex = findArgumentStartIndex(BY_ARG_IDENTIFIER, argument);

        // Note: If /by does not exist, the description is the entire argument string.
        boolean hasTextBeforeByDate = byDateStartIndex == -1
                || hasTextBeforeArgument(byDateStartIndex, argument);

        // If there is no description provided
        if (!hasTextBeforeByDate) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        // If there is no /by
        if (byDateStartIndex == -1) {
            throw new AuroraException(MISSING_BY_ARG_IDENTIFIER + "\n" + USAGE);
        }

        // If there is no details after /by
        if (byDateStartIndex + BY_ARG_IDENTIFIER.length() == argument.length()) {
            throw new AuroraException(MISSING_BY_ARG + "\n" + USAGE);
        }

        String byDateString = argument.substring(byDateStartIndex + BY_ARG_IDENTIFIER.length()).trim();
        LocalDateTime parsedByDate = Parser.of().parseDateTime(byDateString);

        if (parsedByDate == null) {
            throw new AuroraException(INVALID_BY_DATE_ARG + "\n" + USAGE);
        }

        description = argument.substring(0, byDateStartIndex).trim();
        byDate = parsedByDate;

        super.parseArgs(argsList);
    }

}

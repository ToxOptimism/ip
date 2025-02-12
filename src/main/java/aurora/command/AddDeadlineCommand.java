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

        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        String info = argsList[1];

        int byDateStart = -1;
        String beforeBy = info;

        // Account for different combinations of /by usage
        String byArgumentIdentifierWithSpace = BY_ARG_IDENTIFIER + " ";
        String byArgumentIdentifierWithNewLine = BY_ARG_IDENTIFIER + "\n";

        if (info.contains(byArgumentIdentifierWithSpace)) {
            byDateStart = info.indexOf(byArgumentIdentifierWithSpace);
            beforeBy = info.split(byArgumentIdentifierWithSpace)[0].trim();
            description = beforeBy;
        } else if (info.contains(byArgumentIdentifierWithNewLine)) {
            byDateStart = info.indexOf(byArgumentIdentifierWithNewLine);
            beforeBy = info.split(byArgumentIdentifierWithNewLine)[0].trim();
        } else if (info.endsWith(BY_ARG_IDENTIFIER)) {
            byDateStart = info.length() - BY_ARG_IDENTIFIER.length();
            beforeBy = info.substring(0, byDateStart).trim();
        }

        // If there is no description provided
        if (info.trim().isEmpty() || beforeBy.isEmpty()) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        // If there is no /by
        if (byDateStart == -1) {
            throw new AuroraException(MISSING_BY_ARG_IDENTIFIER + "\n" + USAGE);

        // If there is no details after /to
        } else if (byDateStart + BY_ARG_IDENTIFIER.length() == info.length()) {
            throw new AuroraException(MISSING_BY_ARG + "\n" + USAGE);
        }

        String byDateString = info.substring(byDateStart + BY_ARG_IDENTIFIER.length()).trim();
        Parser parser = Parser.of();
        byDate = parser.parseDateTime(byDateString);

        if (byDate == null) {
            throw new AuroraException(INVALID_BY_DATE_ARG + "\n" + USAGE);
        }

        super.parseArgs(argsList);
    }

}

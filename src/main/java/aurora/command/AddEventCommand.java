package aurora.command;

import java.time.LocalDateTime;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.task.Event;
import aurora.task.TaskList;
import aurora.util.Parser;

/**
 * Represents a command to add an Event to the TaskList.
 */
public class AddEventCommand extends AddCommand {

    public static final String CMD_KEYWORD = "event";

    private static final String FROM_ARG_IDENTIFIER = "/from";
    private static final String TO_ARG_IDENTIFIER = "/to";
    private static final String USAGE = "Usage: \"event Description /from From /to To\"";

    // Exception messages
    private static final String MISSING_DESCRIPTION_ARG =
            "Missing argument: \"Description\".";
    private static final String MISSING_FROM_ARG_IDENTIFIER =
            "Missing argument: \"/from From\".";
    private static final String MISSING_TO_ARG_IDENTIFIER =
            "Missing argument: \"/to To\".";
    private static final String WRONG_ARG_IDENTIFIER_ORDER =
            "Invalid format: \"/from From\" must be before \"/to To\".";
    private static final String MISSING_FROM_ARG =
            "Missing argument: \"From\" in \"/from From\".";
    private static final String MISSING_TO_ARG =
            "Missing argument: \"To\" in \"/to To\".";
    private static final String INVALID_FROM_DATE_ARG =
            "Invalid format: \"From\" must be a valid date format of dd/mm/yyyy hhmm.";
    private static final String INVALID_TO_ARG =
            "Invalid format: \"To\" must be a valid date format of dd/mm/yyyy hhmm.";

    // Event specific fields
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String description;

    /**
     * Executes the command to add an Event to the TaskList.
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

        Event event = new Event(description, fromDate, toDate);
        addToList(event, taskList, storage);
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

        /*
         * Check if argument input is not empty
         */
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        String argument = argsList[1];

        // If argument is trailing white space
        if (argument.trim().isEmpty()) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        /*
         * Check if appropriate argument identifiers are present
         */
        // Note: If /by and /from does not exist, the description is the entire argument string.
        int fromDateStartIndex = findArgumentStartIndex(FROM_ARG_IDENTIFIER, argument);
        int toDateStartIndex = findArgumentStartIndex(TO_ARG_IDENTIFIER, argument);
        boolean hasTextBeforeFromDate = fromDateStartIndex == -1
                || hasTextBeforeArgument(fromDateStartIndex, argument);
        boolean hasTextBeforeToDate = toDateStartIndex == -1
                || hasTextBeforeArgument(fromDateStartIndex, argument);
        boolean hasNoDescription = !hasTextBeforeFromDate || !hasTextBeforeToDate;

        // If there is no description provided
        if (hasNoDescription) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        // If there is no /from
        if (fromDateStartIndex == -1) {
            throw new AuroraException(MISSING_FROM_ARG_IDENTIFIER + "\n" + USAGE);

        // If there is no /to
        } else if (toDateStartIndex == -1) {
            throw new AuroraException(MISSING_TO_ARG_IDENTIFIER + "\n" + USAGE);

        // If format is in wrong order
        } else if (fromDateStartIndex > toDateStartIndex) {
            throw new AuroraException(WRONG_ARG_IDENTIFIER_ORDER + "\n" + USAGE);

        // If there is no details after /from
        } else if (fromDateStartIndex + FROM_ARG_IDENTIFIER.length() == toDateStartIndex) {
            throw new AuroraException(MISSING_FROM_ARG + "\n" + USAGE);

        // If there is no details after /to
        } else if (toDateStartIndex + TO_ARG_IDENTIFIER.length() == argument.length()) {
            throw new AuroraException(MISSING_TO_ARG + "\n" + USAGE);
        }

        /*
         * Attempt to parse dates
         */
        Parser parser = Parser.of();

        String fromDateString = argument.substring(fromDateStartIndex
                + FROM_ARG_IDENTIFIER.length(), toDateStartIndex).trim();
        LocalDateTime parsedFromDate = parser.parseDateTime(fromDateString);
        if (parsedFromDate == null) {
            throw new AuroraException(INVALID_FROM_DATE_ARG + "\n" + USAGE);
        }

        String toDateString = argument.substring(toDateStartIndex
                + TO_ARG_IDENTIFIER.length()).trim();
        LocalDateTime parsedToDate = parser.parseDateTime(toDateString);
        if (parsedToDate == null) {
            throw new AuroraException(INVALID_TO_ARG + "\n" + USAGE);
        }

        /*
         * Parse arguments into commands
         */
        description = argument.substring(0, fromDateStartIndex).trim();
        fromDate = parsedFromDate;
        toDate = parsedToDate;

        super.parseArgs(argsList);
    }

}

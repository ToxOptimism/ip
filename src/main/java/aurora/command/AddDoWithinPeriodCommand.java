package aurora.command;

import java.time.LocalDateTime;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.task.DoWithinPeriod;
import aurora.task.TaskList;
import aurora.util.Parser;

/**
 * Represents a command to add a DoWithinPeriod task to the TaskList.
 */
public class AddDoWithinPeriodCommand extends AddCommand {

    public static final String CMD_KEYWORD = "doWithinPeriod";

    private static final String START_PERIOD_ARG_IDENTIFIER = "/start";
    private static final String END_PERIOD_ARG_IDENTIFIER = "/end";
    private static final String USAGE = "Usage: \"doWithinPeriod Description /start Start /end End\"";

    // Exception messages
    private static final String MISSING_DESCRIPTION_ARG =
            "Missing argument: \"Description\".";
    private static final String MISSING_START_PERIOD_ARG_IDENTIFIER =
            "Missing argument: \"/start Start\".";
    private static final String MISSING_END_PERIOD_ARG_IDENTIFIER =
            "Missing argument: \"/end End\".";
    private static final String WRONG_ARG_IDENTIFIER_ORDER =
            "Invalid format: \"/start Start\" must be before \"/end End\".";
    private static final String MISSING_START_PERIOD_ARG =
            "Missing argument: \"Start\" in \"/start Start\".";
    private static final String MISSING_END_PERIOD_ARG =
            "Missing argument: \"End\" in \"/end End\".";
    private static final String INVALID_START_PERIOD_DATE_ARG =
            "Invalid format: \"Start\" must be a valid date format of dd/mm/yyyy hhmm.";
    private static final String INVALID_END_PERIOD_DATE_ARG =
            "Invalid format: \"End\" must be a valid date format of dd/mm/yyyy hhmm.";

    // Event specific fields
    private LocalDateTime startPeriodDate;
    private LocalDateTime endPeriodDate;
    private String description;

    /**
     * Executes the command to add a DoWithinPeriod task to the TaskList.
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

        DoWithinPeriod doWithinPeriodTask = new DoWithinPeriod(description, startPeriodDate, endPeriodDate);
        addToList(doWithinPeriodTask, taskList, storage);
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
        // Note: If /start and /end does not exist, the description is the entire argument string.
        int startPeriodDateStartIndex = findArgumentStartIndex(START_PERIOD_ARG_IDENTIFIER, argument);
        int endPeriodDateStartIndex = findArgumentStartIndex(END_PERIOD_ARG_IDENTIFIER, argument);
        boolean hasTextBeforeFromDate = startPeriodDateStartIndex == -1
                || hasTextBeforeArgument(startPeriodDateStartIndex, argument);
        boolean hasTextBeforeToDate = endPeriodDateStartIndex == -1
                || hasTextBeforeArgument(startPeriodDateStartIndex, argument);
        boolean hasNoDescription = !hasTextBeforeFromDate || !hasTextBeforeToDate;

        // If there is no description provided
        if (hasNoDescription) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        // If there is no /start
        if (startPeriodDateStartIndex == -1) {
            throw new AuroraException(MISSING_START_PERIOD_ARG_IDENTIFIER + "\n" + USAGE);

        // If there is no /end
        } else if (endPeriodDateStartIndex == -1) {
            throw new AuroraException(MISSING_END_PERIOD_ARG_IDENTIFIER + "\n" + USAGE);

        // If format is in wrong order
        } else if (startPeriodDateStartIndex > endPeriodDateStartIndex) {
            throw new AuroraException(WRONG_ARG_IDENTIFIER_ORDER + "\n" + USAGE);

        // If there is no details after /from
        } else if (startPeriodDateStartIndex + START_PERIOD_ARG_IDENTIFIER.length() == endPeriodDateStartIndex) {
            throw new AuroraException(MISSING_START_PERIOD_ARG + "\n" + USAGE);

        // If there is no details after /to
        } else if (endPeriodDateStartIndex + END_PERIOD_ARG_IDENTIFIER.length() == argument.length()) {
            throw new AuroraException(MISSING_END_PERIOD_ARG + "\n" + USAGE);
        }

        /*
         * Attempt to parse dates
         */
        Parser parser = Parser.of();

        String startPeriodDateString = argument.substring(startPeriodDateStartIndex
                + END_PERIOD_ARG_IDENTIFIER.length(), endPeriodDateStartIndex).trim();
        LocalDateTime parsedStartPeriodDate = parser.parseDateTime(startPeriodDateString);
        if (parsedStartPeriodDate == null) {
            throw new AuroraException(INVALID_START_PERIOD_DATE_ARG + "\n" + USAGE);
        }

        String endPeriodDateString = argument.substring(endPeriodDateStartIndex
                + START_PERIOD_ARG_IDENTIFIER.length()).trim();
        LocalDateTime parsedEndPeriodDate = parser.parseDateTime(endPeriodDateString);
        if (parsedEndPeriodDate == null) {
            throw new AuroraException(INVALID_END_PERIOD_DATE_ARG + "\n" + USAGE);
        }

        /*
         * Parse arguments into commands
         */
        description = argument.substring(0, startPeriodDateStartIndex).trim();
        startPeriodDate = parsedStartPeriodDate;
        endPeriodDate = parsedEndPeriodDate;

        super.parseArgs(argsList);
    }

}

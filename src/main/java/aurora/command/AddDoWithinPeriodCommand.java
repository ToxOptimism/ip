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

        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        String info = argsList[1];

        int startPeriodDateStart = -1;
        String beforeStartPeriod = info;

        // Account for different combinations of /from usage
        String startPeriodArgumentIdentifierWithSpace = START_PERIOD_ARG_IDENTIFIER + " ";
        String startPeriodArgumentIdentifierWithNewLine = START_PERIOD_ARG_IDENTIFIER + "\n";

        if (info.contains(startPeriodArgumentIdentifierWithSpace)) {
            startPeriodDateStart = info.indexOf(startPeriodArgumentIdentifierWithSpace);
            beforeStartPeriod = info.split(startPeriodArgumentIdentifierWithSpace)[0].trim();

        } else if (info.contains(startPeriodArgumentIdentifierWithNewLine)) {
            startPeriodDateStart = info.indexOf(startPeriodArgumentIdentifierWithNewLine);
            beforeStartPeriod = info.split(startPeriodArgumentIdentifierWithNewLine)[0].trim();

        } else if (info.endsWith(START_PERIOD_ARG_IDENTIFIER)) {
            startPeriodDateStart = info.length() - START_PERIOD_ARG_IDENTIFIER.length();
            beforeStartPeriod = info.substring(0, info.length() - START_PERIOD_ARG_IDENTIFIER.length()).trim();
        }

        int endPeriodDateStart = -1;
        String beforeEndPeriod = info;

        // Account for different combinations of /to usage
        String endPeriodArgumentIdentifierWithSpace = END_PERIOD_ARG_IDENTIFIER + " ";
        String endPeriodArgumentIdentifierWithNewLine = END_PERIOD_ARG_IDENTIFIER + "\n";

        if (info.contains(endPeriodArgumentIdentifierWithSpace)) {
            endPeriodDateStart = info.indexOf(endPeriodArgumentIdentifierWithSpace);
            beforeEndPeriod = info.split(endPeriodArgumentIdentifierWithSpace)[0].trim();
        } else if (info.contains(endPeriodArgumentIdentifierWithNewLine)) {
            endPeriodDateStart = info.indexOf(endPeriodArgumentIdentifierWithNewLine);
            beforeEndPeriod = info.split(endPeriodArgumentIdentifierWithNewLine)[0].trim();
        } else if (info.endsWith(END_PERIOD_ARG_IDENTIFIER)) {
            endPeriodDateStart = info.length() - END_PERIOD_ARG_IDENTIFIER.length();
            beforeEndPeriod = info.substring(0, endPeriodDateStart).trim();
        }

        // If there is no description provided
        if (info.trim().isEmpty() || beforeStartPeriod.isEmpty() || beforeEndPeriod.isEmpty()) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        // If there is no /from
        if (startPeriodDateStart == -1) {
            throw new AuroraException(MISSING_START_PERIOD_ARG_IDENTIFIER + "\n" + USAGE);

        // If there is no /to
        } else if (endPeriodDateStart == -1) {
            throw new AuroraException(MISSING_END_PERIOD_ARG_IDENTIFIER + "\n" + USAGE);

        // If format is in wrong order
        } else if (startPeriodDateStart > endPeriodDateStart) {
            throw new AuroraException(WRONG_ARG_IDENTIFIER_ORDER + "\n" + USAGE);

        // If there is no details after /from
        } else if (startPeriodDateStart + START_PERIOD_ARG_IDENTIFIER.length() == beforeEndPeriod.length()) {
            throw new AuroraException(MISSING_START_PERIOD_ARG + "\n" + USAGE);

        // If there is no details after /to
        } else if (endPeriodDateStart + END_PERIOD_ARG_IDENTIFIER.length() == info.length()) {
            throw new AuroraException(MISSING_END_PERIOD_ARG + "\n" + USAGE);
        }

        description = info.substring(0, startPeriodDateStart).trim();
        String startPeriodDateString = info.substring(startPeriodDateStart + START_PERIOD_ARG_IDENTIFIER.length(),
                endPeriodDateStart).trim();
        String endPeriodDateString = info.substring(endPeriodDateStart + END_PERIOD_ARG_IDENTIFIER.length())
                .trim();
        Parser parser = Parser.of();
        startPeriodDate = parser.parseDateTime(startPeriodDateString);
        endPeriodDate = parser.parseDateTime(endPeriodDateString);

        if (startPeriodDate == null) {
            throw new AuroraException(INVALID_START_PERIOD_DATE_ARG + "\n" + USAGE);
        }

        if (endPeriodDate == null) {
            throw new AuroraException(INVALID_END_PERIOD_DATE_ARG + "\n" + USAGE);
        }

        super.parseArgs(argsList);
    }

}

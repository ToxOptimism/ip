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
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        String info = argsList[1];

        int fromDateStart = -1;
        String beforeFrom = info;

        // Account for different combinations of /from usage
        String fromArgumentIdentifierWithSpace = FROM_ARG_IDENTIFIER + " ";
        String fromArgumentIdentifierWithNewLine = FROM_ARG_IDENTIFIER + "\n";

        if (info.contains(fromArgumentIdentifierWithSpace)) {
            fromDateStart = info.indexOf(fromArgumentIdentifierWithSpace);
            beforeFrom = info.split(fromArgumentIdentifierWithSpace)[0].trim();

        } else if (info.contains(fromArgumentIdentifierWithNewLine)) {
            fromDateStart = info.indexOf(fromArgumentIdentifierWithNewLine);
            beforeFrom = info.split(fromArgumentIdentifierWithNewLine)[0].trim();

        } else if (info.endsWith(FROM_ARG_IDENTIFIER)) {
            fromDateStart = info.length() - FROM_ARG_IDENTIFIER.length();
            beforeFrom = info.substring(0, info.length() - FROM_ARG_IDENTIFIER.length()).trim();
        }

        int toDateStart = -1;
        String beforeTo = info;

        // Account for different combinations of /to usage
        String toArgumentIdentifierWithSpace = TO_ARG_IDENTIFIER + " ";
        String toArgumentIdentifierWithNewLine = TO_ARG_IDENTIFIER + "\n";

        if (info.contains(toArgumentIdentifierWithSpace)) {
            toDateStart = info.indexOf(toArgumentIdentifierWithSpace);
            beforeTo = info.split(toArgumentIdentifierWithSpace)[0].trim();
        } else if (info.contains(toArgumentIdentifierWithNewLine)) {
            toDateStart = info.indexOf(toArgumentIdentifierWithNewLine);
            beforeTo = info.split(toArgumentIdentifierWithNewLine)[0].trim();
        } else if (info.endsWith(TO_ARG_IDENTIFIER)) {
            toDateStart = info.length() - TO_ARG_IDENTIFIER.length();
            beforeTo = info.substring(0, toDateStart).trim();
        }

        // If there is no description provided
        if (info.trim().isEmpty() || beforeFrom.isEmpty() || beforeTo.isEmpty()) {
            throw new AuroraException(MISSING_DESCRIPTION_ARG + "\n" + USAGE);
        }

        // If there is no /from
        if (fromDateStart == -1) {
            throw new AuroraException(MISSING_FROM_ARG_IDENTIFIER + "\n" + USAGE);

        // If there is no /to
        } else if (toDateStart == -1) {
            throw new AuroraException(MISSING_TO_ARG_IDENTIFIER + "\n" + USAGE);

        // If format is in wrong order
        } else if (fromDateStart > toDateStart) {
            throw new AuroraException(WRONG_ARG_IDENTIFIER_ORDER + "\n" + USAGE);

        // If there is no details after /from
        } else if (fromDateStart + 5 == beforeTo.length()) {
            throw new AuroraException(MISSING_FROM_ARG + "\n" + USAGE);

        // If there is no details after /to
        } else if (toDateStart + 3 == info.length()) {
            throw new AuroraException(MISSING_TO_ARG + "\n" + USAGE);
        }

        description = info.substring(0, fromDateStart).trim();
        String fromDateString = info.substring(fromDateStart + 5, toDateStart).trim();
        String toDateString = info.substring(toDateStart + 3).trim();
        Parser parser = Parser.of();
        fromDate = parser.parseDateTime(fromDateString);
        toDate = parser.parseDateTime(toDateString);

        if (fromDate == null) {
            throw new AuroraException(INVALID_FROM_DATE_ARG + "\n" + USAGE);
        }

        if (toDate == null) {
            throw new AuroraException(INVALID_TO_ARG + "\n" + USAGE);
        }

        super.parseArgs(argsList);
    }

}

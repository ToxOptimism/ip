package aurora.command;

import java.time.LocalDateTime;
import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.task.Event;
import aurora.task.TaskList;
import aurora.util.Parser;

public class AddEventCommand extends AddCommand {

    private LocalDateTime fDate;
    private LocalDateTime tDate;
    private String description;

    @Override
    public void execute(TaskList taskList, Storage storage) throws AuroraException {

        super.execute(taskList, storage);

        Event e = new Event(description, fDate, tDate);
        addToList(e, taskList, storage);
    }

    @Override
    public void parseArgs(String[] argsList) throws AuroraException {
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"event Description /from From /to To\"");
        }

        String info = argsList[1];

        int fromDateStart = -1;
        String beforeFrom = info;

        // Account for different combinations of /from usage
        if (info.contains("/from ")) {
            fromDateStart = info.indexOf("/from ");
            beforeFrom = info.split("/from " )[0].trim();
        } else if (info.contains("/from\n")) {
            fromDateStart = info.indexOf("/from\n");
            beforeFrom = info.split("/from\n" )[0].trim();
        } else if (info.endsWith("/from")) {
            fromDateStart = info.length() - 5;
            beforeFrom = info.substring(0, info.length() - 5).trim();
        }

        int toDateStart = -1;
        String beforeTo = info;

        // Account for different combinations of /to usage
        if (info.contains("/to ")) {
            toDateStart = info.indexOf("/to ");
            beforeTo = info.split("/to " )[0].trim();
        } else if (info.contains("/to\n")) {
            toDateStart = info.indexOf("/to\n");
            beforeTo = info.split("/to\n" )[0].trim();
        } else if (info.endsWith("/to")) {
            toDateStart = info.length() - 3;
            beforeTo = info.substring(0, info.length() - 3).trim();
        }

        // If there is no description provided
        if (info.trim().isEmpty() || beforeFrom.isEmpty() || beforeTo.isEmpty()) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"event Description /from From /to To\"");
        }

        // If there is no /from
        if (fromDateStart == -1) {
            throw new AuroraException("Missing argument: \"/from From\".\nUsage: \"event Description /from From /to To\"");

            // If there is no /to
        } else if (toDateStart == -1) {
            throw new AuroraException("Missing argument: \"/to To\".\nUsage: \"event Description /from From /to To\"");

            // If format is in wrong order
        } else if (fromDateStart > toDateStart) {
            throw new AuroraException("Invalid format: \"/from From\" must be before \"/to To\".\nUsage: \"event Description /from From /to To\"");

            // If there is no details after /from
        } else if (fromDateStart + 5 == beforeTo.length()) {
            throw new AuroraException("Missing argument: \"From\" in \"/from From\".\nUsage: \"event Description /from From /to To\"");

            // If there is no details after /to
        } else if (toDateStart + 3 == info.length()) {
            throw new AuroraException("Missing argument: \"To\" in \"/to To\".\nUsage: \"event Description /from From /to To\"");
        }

        description = info.substring(0, fromDateStart).trim();
        String fromDate = info.substring(fromDateStart + 5, toDateStart).trim();
        String toDate = info.substring(toDateStart + 3).trim();
        Parser parser = Parser.of();
        fDate = parser.parseDateTime(fromDate);
        tDate = parser.parseDateTime(toDate);

        if (fDate == null) {
            throw new AuroraException("Invalid format: \"From\" must be a valid date format of dd/mm/yyyy hhmm.\nUsage: \"event Description /from From /to To\"");
        }

        if (tDate == null) {
            throw new AuroraException("Invalid format: \"To\" must be a valid date format of dd/mm/yyyy hhmm.\nUsage: \"event Description /from From /to To\"");
        }

        super.parseArgs(argsList);
    }

}

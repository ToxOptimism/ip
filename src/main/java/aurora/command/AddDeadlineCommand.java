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
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: \"Description\".\n"
                    + "Usage: \"deadline Description /by By\"");
        }

        String info = argsList[1];

        int byDateStart = -1;
        String beforeBy = info;

        // Account for different combinations of /by usage
        if (info.contains("/by ")) {
            byDateStart = info.indexOf("/by ");
            beforeBy = info.split("/by ")[0].trim();
            description = beforeBy;
        } else if (info.contains("/by\n")) {
            byDateStart = info.indexOf("/by\n");
            beforeBy = info.split("/by\n")[0].trim();
        } else if (info.endsWith("/by")) {
            byDateStart = info.length() - 3;
            beforeBy = info.substring(0, info.length() - 3).trim();
        }

        // If there is no description provided
        if (info.trim().isEmpty() || beforeBy.isEmpty()) {
            throw new AuroraException("Missing argument: \"Description\".\n"
                    + "Usage: \"deadline Description /by By\"");
        }

        // If there is no /by
        if (byDateStart == -1) {
            throw new AuroraException("Missing argument: \"/by By\"."
                    + "\nUsage: \"deadline Description /by By\"");

        // If there is no details after /to
        } else if (byDateStart + 3 == info.length()) {
            throw new AuroraException("Missing argument: \"By\" in \"/by By\".\n"
                    + "Usage: \"deadline Description /by By\"");
        }

        String byDateString = info.substring(byDateStart + 3).trim();
        Parser parser = Parser.of();
        byDate = parser.parseDateTime(byDateString);

        if (byDate == null) {
            throw new AuroraException("Invalid format: \"By\" must be a valid date format of dd/mm/yyyy hhmm.\n"
                    + "Usage: \"deadline Description /by By\"");
        }

        super.parseArgs(argsList);
    }

}

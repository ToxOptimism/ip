package aurora.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import aurora.command.AddDeadlineCommand;
import aurora.command.AddDoWithinPeriodCommand;
import aurora.command.AddEventCommand;
import aurora.command.AddToDoCommand;
import aurora.command.ByeCommand;
import aurora.command.Command;
import aurora.command.DeleteCommand;
import aurora.command.FindCommand;
import aurora.command.ListCommand;
import aurora.command.MarkCommand;
import aurora.command.UnmarkCommand;
import aurora.exception.AuroraException;
import aurora.task.Deadline;
import aurora.task.DoWithinPeriod;
import aurora.task.Event;
import aurora.task.Task;
import aurora.task.ToDo;

/**
 * Represents a utility class that parses various input formats.
 */
public class Parser {

    private static final String UNKNOWN_COMMAND = "Unknown command: %s";
    private static final String FILE_TASK_MARKED = "1";

    // Expected input format of date time
    private static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    // The singleton instance
    private static final Parser SINGLETON = new Parser();

    /**
     * Constructs a new Parser.
     */
    protected Parser() {}

    /**
     * Gets the singleton instance of the parser.
     *
     * @return SINGLETON the singleton instance.
     */
    public static Parser of() {
        return SINGLETON;
    }

    /**
     * Parses the task list file into a list of tasks.
     *
     * @param lines the lines of the task list file.
     * @return parsedTaskList the list of tasks.
     */
    public List<Task> parseTaskListFile(List<String> lines) {
        List<Task> parsedTaskList = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(" \\| ");
            Task task;

            // Assumption: data has not been maliciously manipulated
            switch (parts[0]) {
            case ToDo.TASK_KEYWORD:
                task = new ToDo(parts[2]);
                break;
            case Deadline.TASK_KEYWORD:
                task = new Deadline(parts[2], parseDateTime(parts[3]));
                break;
            case Event.TASK_KEYWORD:
                task = new Event(parts[2], parseDateTime(parts[3]), parseDateTime(parts[4]));
                break;
            case DoWithinPeriod.TASK_KEYWORD:
                task = new DoWithinPeriod(parts[2], parseDateTime(parts[3]), parseDateTime(parts[4]));
                break;
            default:
                continue;
            }

            parsedTaskList.add(task);

            if (parts[1].equals(FILE_TASK_MARKED)) {
                task.markAsDone();
            }
        }

        return parsedTaskList;
    }

    /**
     * Checks if a string can be parsed into an integer.
     *
     * @param input the string to check.
     * @return true if the string can be parsed into an integer.
     */
    public boolean canParseInt(String input) {

        assert(input != null) : "input is null.";

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Parses a string into a LocalDateTime object.
     *
     * @param input the string to check.
     * @return LocalDateTime if the string can be parsed into a LocalDateTime object.
     */
    public LocalDateTime parseDateTime(String input) {

        assert(input != null) : "input is null.";

        try {
            return LocalDateTime.parse(input, inputFormat);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param input the user input.
     * @return Command the corresponding command.
     * @throws AuroraException if the user input is invalid.
     */
    public Command parseCommand(String input) throws AuroraException {

        assert(input != null) : "input is null.";

        String trimmedInput = input.trim();
        String[] argsList = trimmedInput.split(" ", 2);
        Command command;
        switch (argsList[0]) {
        case ByeCommand.CMD_KEYWORD:
            command = new ByeCommand();
            break;
        case FindCommand.CMD_KEYWORD:
            command = new FindCommand();
            break;
        case ListCommand.CMD_KEYWORD:
            command = new ListCommand();
            break;
        case MarkCommand.CMD_KEYWORD:
            command = new MarkCommand();
            break;
        case UnmarkCommand.CMD_KEYWORD:
            command = new UnmarkCommand();
            break;
        case AddToDoCommand.CMD_KEYWORD:
            command = new AddToDoCommand();
            break;
        case AddDeadlineCommand.CMD_KEYWORD:
            command = new AddDeadlineCommand();
            break;
        case AddEventCommand.CMD_KEYWORD:
            command = new AddEventCommand();
            break;
        case AddDoWithinPeriodCommand.CMD_KEYWORD:
            command = new AddDoWithinPeriodCommand();
            break;
        case DeleteCommand.CMD_KEYWORD:
            command = new DeleteCommand();
            break;
        default:
            String message = String.format(UNKNOWN_COMMAND, trimmedInput);
            throw new AuroraException(message);
        }

        command.parseArgs(argsList);

        return command;
    }
}

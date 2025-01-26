package aurora.util;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import aurora.exception.AuroraException;
import aurora.task.Task;
import aurora.task.ToDo;
import aurora.task.Event;
import aurora.task.Deadline;
import aurora.command.Command;
import aurora.command.AddDeadlineCommand;
import aurora.command.AddEventCommand;
import aurora.command.AddToDoCommand;
import aurora.command.ByeCommand;
import aurora.command.DeleteCommand;
import aurora.command.ListCommand;
import aurora.command.MarkCommand;
import aurora.command.UnmarkCommand;

/**
 * Represents a utility class that parses various input formats.
 */
public class Parser {

    // Expected input format of date time
    private static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    // The singleton instance
    private static final Parser SINGLETON = new Parser();

    /**
     * Constructor for Parser
     */
    protected Parser() {}

    /**
     * Factory method of parser.
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
            Task t;

            // Assumption: data has not been maliciously manipulated
            switch (parts[0]) {
            case "T":
                t = new ToDo(parts[2]);
                break;
            case "D":
                t = new Deadline(parts[2], parseDateTime(parts[3]));
                break;
            case "E":
                t = new Event(parts[2], parseDateTime(parts[3]), parseDateTime(parts[4]));
                break;
            default:
                continue;
            }

            parsedTaskList.add(t);

            if (parts[1].equals("1")) {
                t.markAsDone();
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
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses a string into a LocalDateTime object.
     *
     * @param input the string to check.
     * @return LocalDateTime if the string can be parsed into a LocalDateTime object.
     */
    public LocalDateTime parseDateTime(String input) {

        try {
            if (input == null) {
                return null;
            }
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
        String[] argsList = input.split(" ", 2);
        Command command = null;
        switch (argsList[0]) {
        case "bye":
            command = new ByeCommand();
            break;
        case "list":
            command = new ListCommand();
            break;
        case "mark":
            command = new MarkCommand();
            break;
        case "unmark":
            command = new UnmarkCommand();
            break;
        case "todo":
            command = new AddToDoCommand();
            break;
        case "deadline":
            command = new AddDeadlineCommand();
            break;
        case "event":
            command = new AddEventCommand();
            break;
        case "delete":
            command = new DeleteCommand();
            break;
        default:
            throw new AuroraException("Unknown command: " + input);
        }

        command.parseArgs(argsList);

        return command;
    }
}

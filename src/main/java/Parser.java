import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public static List<Task> parseTaskListFile(List<String> lines) {
        List<Task> parsedTaskList = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(" \\| ");
            Task t = null;

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

    public static boolean canParseInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static LocalDateTime parseDateTime(String input) {

        try {
            return LocalDateTime.parse(input, inputFormat);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public static Command parseCommand(String input) throws AuroraException {
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

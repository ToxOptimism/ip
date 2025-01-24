import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

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
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

        try {
            return LocalDateTime.parse(input, inputFormat);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}

import java.time.LocalDateTime;

public class AddDeadlineCommand extends AddCommand {

    private LocalDateTime bDate;
    private String description;

    @Override
    public void execute(TaskList taskList) throws AuroraException {

        super.execute(taskList);

        Deadline d = new Deadline(description, bDate);
        addToList(d, taskList);
    }

    @Override
    public void parseArgs(String[] argsList) throws AuroraException {
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"deadline Description /by By\"");
        }

        String info = argsList[1];

        int byDateStart = -1;
        String beforeBy = info;

        // Account for different combinations of /by usage
        if (info.contains("/by ")) {
            byDateStart = info.indexOf("/by ");
            beforeBy = info.split("/by " )[0].trim();
            description = beforeBy;
        } else if (info.contains("/by\n")) {
            byDateStart = info.indexOf("/by\n");
            beforeBy = info.split("/by\n" )[0].trim();
        } else if (info.endsWith("/by")) {
            byDateStart = info.length() - 3;
            beforeBy = info.substring(0, info.length() - 3).trim();
        }

        // If there is no description provided
        if (info.trim().isEmpty() || beforeBy.isEmpty()) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"deadline Description /by By\"");
        }

        // If there is no /by
        if (byDateStart == -1) {
            throw new AuroraException("Missing argument: \"/by By\".\nUsage: \"deadline Description /by By\"");

            // If there is no details after /to
        } else if (byDateStart + 3 == info.length()) {
            throw new AuroraException("Missing argument: \"By\" in \"/by By\".\nUsage: \"deadline Description /by By\"");
        }

        String byDate = info.substring(byDateStart + 3).trim();
        bDate = Parser.parseDateTime(byDate);

        if (bDate == null) {
            throw new AuroraException("Invalid format: \"By\" must be a valid date format of dd/mm/yyyy hhmm.\nUsage: \"deadline Description /by By\"");
        }

        super.parseArgs(argsList);
    }

}

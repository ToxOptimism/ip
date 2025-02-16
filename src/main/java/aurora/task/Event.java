package aurora.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a description, a start date and an end date.
 */
public class Event extends Task {

    public static final String TASK_KEYWORD = "E";

    // Event specific fields
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");
    private final DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Constructs a new Event task.
     *
     * @param description the description of the Event task.
     * @param startDate the start date of the Event task.
     * @param endDate the end date of the Event task.
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);

        assert(startDate != null) : "startDate is null.";
        assert(endDate != null) : "endDate is null.";

        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Gets event in file format string representation.
     *
     * @return the string representation of the Event task in file format.
     */
    @Override
    public String toFileFormat() {
        return TASK_KEYWORD + " | " + super.toFileFormat() + " | " + startDate.format(fileFormat)
                + " | " + endDate.format(fileFormat);
    }

    /**
     * Gets event in display string representation.
     *
     * @return the string representation of the Event task in display format.
     */
    @Override
    public String toString() {
        return "[" + TASK_KEYWORD + "]" + super.toString() + " (from: " + startDate.format(outputFormat)
                + " to: " + endDate.format(outputFormat) + ")";
    }
}

package aurora.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Do Within Period task with a description, a start date and an end date.
 * A Do Within Period task is a task where the user intends to finish the task within a specified window of time.
 */
public class DoWithinPeriod extends Task {

    public static final String TASK_KEYWORD = "P";

    // Event specific fields
    private final LocalDateTime startPeriodDate;
    private final LocalDateTime endPeriodDate;
    private final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");
    private final DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Constructor for Event.
     *
     * @param description the description of the Deadline task.
     * @param startDate the start date of the Deadline task.
     * @param endDate the end date of the Deadline task.
     */
    public DoWithinPeriod(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);

        assert(startDate != null) : "startDate is null.";
        assert(endDate != null) : "endDate is null.";

        this.startPeriodDate = startDate;
        this.endPeriodDate = endDate;
    }

    /**
     * Get event in file format string representation.
     *
     * @return the string representation of the Event task in file format.
     */
    @Override
    public String toFileFormat() {
        return TASK_KEYWORD + " | " + super.toFileFormat() + " | " + startPeriodDate.format(fileFormat)
                + " | " + endPeriodDate.format(fileFormat);
    }

    /**
     * Get event in display string representation.
     *
     * @return the string representation of the Event task in display format.
     */
    @Override
    public String toString() {
        return "[" + TASK_KEYWORD + "]" + super.toString() + " (from: " + startPeriodDate.format(outputFormat)
                + " to: " + endPeriodDate.format(outputFormat) + ")";
    }
}

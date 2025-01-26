package aurora.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Event task with a description, a start date and an end date.
 */
public class Event extends Task {

    // Event specific fields
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");
    private DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Constructor for Event.
     *
     * @param description the description of the Deadline task.
     * @param startDate the start date of the Deadline task.
     * @param endDate the end date of the Deadline task.
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Get event in file format string representation.
     *
     * @return the string representation of the Event task in file format.
     */
    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + startDate.format(fileFormat) + " | " + endDate.format(fileFormat);
    }

    /**
     * Get event in display string representation.
     *
     * @return the string representation of the Event task in display format.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDate.format(outputFormat) + " to: " + endDate.format(outputFormat) + ")";
    }
}
package aurora.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a description and a deadline.
 */
public class Deadline extends Task {

    // Deadline specific fields
    private LocalDateTime byDate;
    private DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");
    private DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Constructor for Deadline.
     *
     * @param description the description of the Deadline task.
     * @param byDate the deadline of the Deadline task.
     */
    public Deadline(String description, LocalDateTime byDate) {
        super(description);

        assert(byDate != null) : "byDate is null.";

        this.byDate = byDate;
    }

    /**
     * Get deadline in file format string representation.
     *
     * @return the string representation of the Deadline task in file format.
     */
    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat()
                + " | " + byDate.format(fileFormat);
    }

    /**
     * Get deadline in display string representation.
     *
     * @return the string representation of the Deadline task in display format.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDate.format(outputFormat) + ")";
    }
}

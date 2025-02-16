package aurora.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a description and a deadline.
 */
public class Deadline extends Task {

    public static final String TASK_KEYWORD = "D";

    // Deadline specific fields
    private final LocalDateTime byDate;
    private final DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");
    private final DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /**
     * Constructs a new Deadline Task.
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
     * Gets deadline in file format string representation.
     *
     * @return the string representation of the Deadline task in file format.
     */
    @Override
    public String toFileFormat() {
        return TASK_KEYWORD + " | " + super.toFileFormat() + " | " + byDate.format(fileFormat);
    }

    /**
     * Gets deadline in display string representation.
     *
     * @return the string representation of the Deadline task in display format.
     */
    @Override
    public String toString() {
        return "[" + TASK_KEYWORD + "]" + super.toString() + " (by: " + byDate.format(outputFormat) + ")";
    }
}

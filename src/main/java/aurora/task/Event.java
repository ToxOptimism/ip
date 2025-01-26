package aurora.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    protected DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");
    protected DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " + startDate.format(fileFormat)
                + " | " + endDate.format(fileFormat);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDate.format(outputFormat)
                + " to: " + endDate.format(outputFormat) + ")";
    }
}

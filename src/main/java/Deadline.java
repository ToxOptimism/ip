import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime byDate;
    protected DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMM d yyyy h:mma");
    protected DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public Deadline(String description, LocalDateTime byDate) {
        super(description);
        this.byDate = byDate;
    }

    @Override
    public String toFileFormat() {
        return "D | " + super.toFileFormat() + " | " + byDate.format(fileFormat);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDate.format(outputFormat) + ")";
    }
}
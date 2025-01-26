package aurora.task;

/**
 * Represents an ToDo task with a description.
 */
public class ToDo extends Task {

    /**
     * Constructor for ToDo.
     *
     * @param description the description of the ToDo task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Get todo in file format string representation.
     *
     * @return the string representation of the ToDo task in file format.
     */
    @Override
    public String toFileFormat() {
        return "T | " + super.toFileFormat();
    }

    /**
     * Get todo in display string representation.
     *
     * @return the string representation of the ToDo task in display format.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}

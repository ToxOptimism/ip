package aurora.task;

/**
 * Represents an ToDo task with a description.
 */
public class ToDo extends Task {

    public static final String TASK_KEYWORD = "T";

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
        return TASK_KEYWORD + " | " + super.toFileFormat();
    }

    /**
     * Get todo in display string representation.
     *
     * @return the string representation of the ToDo task in display format.
     */
    @Override
    public String toString() {
        return "[" + TASK_KEYWORD + "]" + super.toString();
    }

}

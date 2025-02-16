package aurora.task;

/**
 * Represents a Task with a description and a done status.
 */
public class Task {

    // Task specific fields
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task.
     *
     * @param description the description of the Task.
     */
    public Task(String description) {

        assert(description != null) : "description is null.";

        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets status icon of the task.
     *
     * @return the status icon of whether the task is done.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Gets status icon of the task in file format.
     *
     * @return the status icon of whether the task is done in file format.
     */
    public String getStatusFileFormat() {
        return (isDone ? "1" : "0"); // mark done task with 1
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmarkAsDone() {
        isDone = false;
    }


    /**
     * Checks if the task description contains the keyword.
     *
     * @param keyword the keyword to search for.
     * @return true if the keyword is found in the description, false otherwise.
     */
    public boolean hasKeyword(String keyword) {
        return description.contains(keyword);
    }

    /**
     * Gets task in file format string representation.
     *
     * @return the string representation of the Task in file format.
     */
    public String toFileFormat() {
        return (getStatusFileFormat() + " | " + description);
    }

    /**
     * Gets task in display string representation.
     *
     * @return the string representation of the Task in display format.
     */
    @Override
    public String toString() {
        return ("[" + getStatusIcon() + "] " + description);
    }
}

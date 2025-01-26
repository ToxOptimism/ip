package aurora.task;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getStatusFileFormat() {
        return (isDone ? "1" : "0"); // mark done task with 1
    }

    public void markAsDone() {
        isDone = true;
    }

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

    public String toFileFormat() {
        return (getStatusFileFormat() + " | " + description);
    }

    @Override
    public String toString() {
        return ("[" + getStatusIcon() + "] " + description);
    }
}
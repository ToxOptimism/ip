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

    public String toFileFormat() {
        return (getStatusFileFormat() + " | " + description);
    }

    @Override
    public String toString() {
        return ("[" + getStatusIcon() + "] " + description);
    }
}
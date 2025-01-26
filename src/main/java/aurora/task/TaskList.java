package aurora.task;

import java.util.ArrayList;
import java.util.List;
import aurora.exception.AuroraException;

/**
 * Represents a list of tasks with methods for manipulating the list.
 */
public class TaskList {

    // TaskList specific fields
    private ArrayList<Task> taskList;

    /**
     * Constructor for TaskList.
     */
    public TaskList() {
        taskList = new ArrayList<>();
    }

    /**
     * Gets the size of the list.
     *
     * @return the size of the list.
     */
    public int getSize() {
        return taskList.size();
    }

    /**
     * Gets the task at the specified index.
     *
     * @param index the 1-based index of the task to get.
     * @return Task the task at specified index.
     * @throws AuroraException if the index is out of bounds.
     */
    public Task getTask(int index) throws AuroraException {

        validateIndex(index); // throws an exception

        return taskList.get(index-1);
    }

    /**
     * Adds a task to the list.
     *
     * @param t the task to be added.
     */
    public void addToList(Task t) {
        taskList.add(t);
    }

    /**
     * Deletes a task from the list.
     *
     * @param index the 1-based index of the task to be deleted.
     * @return t the task that was deleted.
     * @throws AuroraException if the index is out of bounds.
     */
    public Task deleteFromList(int index) throws AuroraException {

        validateIndex(index); // throws an exception

        Task t = taskList.remove(index - 1);

        return t;

    }

    /**
     * Validates if the 1-based index is within the bounds of the list.
     *
     * @param index the 1-based index to be validated.
     * @throws AuroraException if the index is out of bounds.
     */
    public void validateIndex(int index) throws AuroraException {
        if (taskList.isEmpty()) {
            throw new AuroraException("Task List is empty. Unable to run command.");
        } else if (index < 1 || index > taskList.size()) {
            throw new AuroraException("Argument provided \"" + index + "\" must be between bounds of 1 and " + taskList.size() + ".");
        }
    }

    /**
     * Marks a task as done.
     *
     * @param index the 1-based index of the task to be marked as done.
     * @return t the task that was marked as done.
     * @throws AuroraException if the index is out of bounds.
     */
    public Task markTaskDone(int index) throws AuroraException {

        validateIndex(index); // throws an exception

        Task t = taskList.get(index - 1);
        t.markAsDone();

        return t;
    }

    /**
     * Marks a task as not done.
     *
     * @param index the 1-based index of the task to be marked as not done.
     * @return t the task that was marked as not done.
     * @throws AuroraException if the index is out of bounds.
     */
    public Task unmarkTaskDone(int index) throws AuroraException {

        validateIndex(index); // throws an exception

        Task t = taskList.get(index - 1);
        t.unmarkAsDone();

        return t;

    }

    /**
     * Get taskList in file format string representation.
     *
     * @return the string representation of the TaskList in file format.
     */
    public List<String> toFileFormat() {
        List<String> lines = new ArrayList<>();
        for (Task task : taskList) {
            lines.add(task.toFileFormat());
        }

        return lines;
    }

    /**
     * Get taskList in display string representation.
     *
     * @return the string representation of the TaskList in display format.
     */
    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder();
        for (int i = 1; i <= taskList.size(); i++) {
            listString.append(i).append(". ").append(taskList.get(i-1).toString()).append("\n");
        }
        listString.delete(listString.length() - 1, listString.length());
        return listString.toString();
    }

}

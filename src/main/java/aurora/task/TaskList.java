package aurora.task;

import java.util.ArrayList;
import java.util.List;

import aurora.exception.AuroraException;

/**
 * Represents a list of tasks with methods for manipulating the list.
 */
public class TaskList {

    // TaskList specific fields
    private final ArrayList<Task> taskList;

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

        return taskList.get(index - 1);
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to be added.
     */
    public void addToList(Task task) {
        taskList.add(task);
    }

    /**
     * Deletes a task from the list.
     *
     * @param index the 1-based index of the task to be deleted.
     * @return task the task that was deleted.
     * @throws AuroraException if the index is out of bounds.
     */
    public Task deleteFromList(int index) throws AuroraException {

        validateIndex(index); // throws an exception

        return taskList.remove(index - 1);

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
            throw new AuroraException("Argument provided \"" + index
                    + "\" must be between bounds of 1 and " + taskList.size() + ".");
        }
    }

    /**
     * Marks a task as done.
     *
     * @param index the 1-based index of the task to be marked as done.
     * @return task the task that was marked as done.
     * @throws AuroraException if the index is out of bounds.
     */
    public Task markTaskDone(int index) throws AuroraException {

        validateIndex(index); // throws an exception

        Task task = taskList.get(index - 1);
        task.markAsDone();

        return task;
    }

    /**
     * Marks a task as not done.
     *
     * @param index the 1-based index of the task to be marked as not done.
     * @return task the task that was marked as not done.
     * @throws AuroraException if the index is out of bounds.
     */
    public Task unmarkTaskDone(int index) throws AuroraException {

        validateIndex(index); // throws an exception

        Task task = taskList.get(index - 1);
        task.unmarkAsDone();

        return task;

    }

    /**
     * Gets list of tasks with a keyword in its description.
     *
     * @param keyword the keyword to search for.
     * @return TaskList containing tasks with the keyword in its description.
     */
    public TaskList findMatchingKeyword(String keyword) {
        TaskList newTaskListObj = new TaskList();
        for (Task task : taskList) {
            if (task.hasKeyword(keyword)) {
                newTaskListObj.addToList(task);
            }
        }

        return newTaskListObj;
    }

    /**
     * Get taskList in file format string representation.
     *
     * @return the list of string representation of tasks in file format.
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
     * @return the list of string representation of tasks in display format.
     */
    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder();
        for (int i = 1; i <= taskList.size(); i++) {
            listString.append(i).append(". ").append(taskList.get(i - 1).toString()).append("\n");
        }
        listString.delete(listString.length() - 1, listString.length());
        return listString.toString();
    }

}

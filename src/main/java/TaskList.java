import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<>();
    }

    public int getSize() {
        return taskList.size();
    }

    public Task getTask(int index) throws AuroraException {

        validateIndex(index); // throws an exception

        return taskList.get(index-1);
    }

    public void addToList(Task t) {
        taskList.add(t);
    }

    public Task deleteFromList(int index) throws AuroraException{

        validateIndex(index); // throws an exception

        Task t = taskList.remove(index - 1);

        return t;

    }

    public void validateIndex(int index) throws AuroraException  {
        if (taskList.isEmpty()) {
            throw new AuroraException("Task List is empty. Unable to run command.");
        } else if (index < 1 || index > taskList.size()) {
            throw new AuroraException("Argument provided \"" + index + "\" must be between bounds of 1 and " + taskList.size() + ".");
        }
    }

    public Task markTaskDone(int index) throws AuroraException {

        validateIndex(index); // throws an exception

        Task t = taskList.get(index - 1);
        t.markAsDone();

        return t;
    }

    public Task unmarkTaskDone(int index) throws AuroraException {

        validateIndex(index); // throws an exception

        Task t = taskList.get(index - 1);
        t.unmarkAsDone();

        return t;

    }

    public List<String> toFileFormat() {
        List<String> lines = new ArrayList<>();
        for (Task task : taskList) {
            lines.add(task.toFileFormat());
        }

        return lines;
    }

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

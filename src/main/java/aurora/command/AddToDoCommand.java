package aurora.command;

import aurora.exception.AuroraException;
import aurora.task.ToDo;
import aurora.task.TaskList;

public class AddToDoCommand extends AddCommand {

    private String description;

    @Override
    public void execute(TaskList taskList) throws AuroraException {

        super.execute(taskList);

        ToDo td = new ToDo(description);
        addToList(td, taskList);

    }

    @Override
    public void parseArgs(String[] argsList) throws AuroraException {
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"todo Description\"");
        }

        description = argsList[1].trim();

        // If there is no description provided
        if (description.isEmpty()) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"todo Description\"");
        }

        super.parseArgs(argsList);
    }

}

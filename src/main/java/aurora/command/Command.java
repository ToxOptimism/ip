package aurora.command;

import java.util.ArrayList;
import java.util.List;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.task.Task;
import aurora.task.TaskList;

/**
 * Represents a command with the ability to parse arguments and be executed.
 */
public abstract class Command {

    // Fields to prevent misuse of commands
    private boolean isArgParsed = false;
    private boolean isCmdExecuted = false;

    /**
     * Executes the command.
     * Sets isCmdExecuted to true if successful.
     *
     * @param taskList the taskList for referencing.
     * @param storage the storage for referencing.
     * @throws AuroraException if the command is executed without parsing or already executed.
     */
    public void execute(TaskList taskList, Storage storage) throws AuroraException {
        if (!isArgParsed) {
            throw new AuroraException("Command not parsed");
        }

        if (isCmdExecuted) {
            throw new AuroraException("Command already executed");
        }

        isCmdExecuted = true;
    }

    /**
     * Parses the arguments for the command.
     * Sets isArgParsed to true if successful.
     *
     * @param argsList the arguments to parse.
     * @throws AuroraException the appropriate exception message if unable to parse arguments.
     */
    public void parseArgs(String[] argsList) throws AuroraException {
        isArgParsed = true;
    }

    /**
     * Appends a task to the storage.
     *
     * @param t the task to append.
     * @param storage the storage to write to.
     * @throws AuroraException if unable to append task to file.
     */
    public void appendTaskListFile(Task t, Storage storage) throws AuroraException {
        List<String> lines = new ArrayList<>();
        lines.add(t.toFileFormat());
        storage.appendTaskListFile(lines);
    }

    /**
     * Overwrites the storage with an amended TaskList.
     *
     * @param taskList the taskList to overwrite with.
     * @param storage the storage to write to.
     * @throws AuroraException if unable to overwrite file.
     */
    public static void overwriteTaskListFile(TaskList taskList, Storage storage) throws AuroraException {
        List<String> lines = taskList.toFileFormat();
        storage.overwriteTaskListFile(lines);
    }
}

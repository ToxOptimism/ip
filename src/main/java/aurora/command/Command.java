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

        assert(isArgParsed) : "The command's args were not parsed yet.";
        assert(!isCmdExecuted) : "The command was already executed.";

        this.isCmdExecuted = true;
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
     * @param task the task to append.
     * @param storage the storage to write to.
     * @throws AuroraException if unable to append task to file.
     */
    public void appendTaskListFile(Task task, Storage storage) throws AuroraException {

        assert(task != null) : "The task is null.";
        assert(storage != null) : "Storage is null.";

        List<String> lines = new ArrayList<>();
        lines.add(task.toFileFormat());
        storage.appendTaskListFile(lines);
    }

    /**
     * Overwrites the storage with an amended TaskList.
     *
     * @param taskList the taskList to overwrite with.
     * @param storage the storage to write to.
     * @throws AuroraException if unable to overwrite file.
     */
    public void overwriteTaskListFile(TaskList taskList, Storage storage) throws AuroraException {

        assert(taskList != null) : "The taskList is null.";
        assert(storage != null) : "Storage is null.";

        List<String> lines = taskList.toFileFormat();
        storage.overwriteTaskListFile(lines);
    }

    /**
     * Finds the start index of the given argument.
     *
     * @param argIdentifier the "/param" to search for
     * @param argument the argument string to search through
     * @return the start index of the argument
     */
    protected int findArgumentStartIndex(String argIdentifier, String argument) {
        int argStartIndex = -1;

        // Account for different combinations of /arg usage
        String argIdentifierWithSpace = argIdentifier + " ";
        String argIdentifierWithNewLine = argIdentifier + "\n";

        if (argument.contains(argIdentifierWithSpace)) {
            argStartIndex = argument.indexOf(argIdentifierWithSpace);

        } else if (argument.contains(argIdentifierWithNewLine)) {
            argStartIndex = argument.indexOf(argIdentifierWithNewLine);

        } else if (argument.endsWith(argIdentifier)) {
            argStartIndex = argument.length() - argIdentifier.length();
        }

        return argStartIndex;
    }

    /**
     * Checks if there is any text before the argument.
     *
     * @param argStartIndex the start index of the argument
     * @param argument the argument string
     * @return true if there is text before the argument, false otherwise
     */
    protected boolean hasTextBeforeArgument(int argStartIndex, String argument) {

        assert argStartIndex >= 0 : "Argument start index is less than 0.";
        assert argument != null : "Arguments is null.";

        String substringBeforeArg = argument.substring(0, argStartIndex);
        return !substringBeforeArg.trim().isEmpty();
    }
}

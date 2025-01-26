package aurora.command;

import java.util.ArrayList;
import java.util.List;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.task.Task;
import aurora.task.TaskList;

public abstract class Command {

    protected boolean isArgParsed = false;
    protected boolean isCmdExecuted = false;

    public boolean isExitCommand() {
        return false;
    }

    public void execute(TaskList taskList, Storage storage) throws AuroraException {
        if (!isArgParsed) {
            throw new AuroraException("Command not parsed");
        }

        if (isCmdExecuted) {
            throw new AuroraException("Command already executed");
        }
    }

    public void parseArgs(String[] argsList) throws AuroraException {
        isArgParsed = true;
    }

    public void appendTaskListFile(Task t, Storage storage) throws AuroraException {
        List<String> lines = new ArrayList<>();
        lines.add(t.toFileFormat());
        storage.appendTaskListFile(lines);
    }

    public static void overwriteTaskListFile(TaskList taskList, Storage storage) throws AuroraException {
        List<String> lines = taskList.toFileFormat();
        storage.overwriteTaskListFile(lines);
    }
}

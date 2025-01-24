import java.util.ArrayList;
import java.util.List;

abstract public class Command {

    protected boolean isArgParsed = false;
    protected boolean isCmdExecuted = false;

    public boolean isExitCommand() {
        return false;
    }

    public void cmdExecuted() {
        isCmdExecuted = true;
    }

    public void execute(TaskList taskList) throws AuroraException {
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

    public void appendTaskListFile(Task t) throws AuroraException {
        List<String> lines = new ArrayList<>();
        lines.add(t.toFileFormat());
        Storage.appendTaskListFile(lines);
    }

    public static void overwriteTaskListFile(TaskList taskList) throws AuroraException {
        List<String> lines = taskList.toFileFormat();
        Storage.overwriteTaskListFile(lines);
    }
}

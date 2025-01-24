public class MarkCommand extends Command {

    private int index;

    @Override
    public void execute(TaskList taskList) throws AuroraException {

        super.execute(taskList);
        Task t = taskList.markTaskDone(index);

        Ui.printMsg("This task has been marked as done:" + "\n" + t);
        overwriteTaskListFile(taskList);
    }

    @Override
    public void parseArgs(String[] argsList) throws AuroraException {
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: \"Description\".\nUsage: \"mark Index\"");

            // Argument provided is not an integer
        } else if (!Parser.canParseInt(argsList[1])) {
            throw new AuroraException("Invalid arguments: index must be a valid integer value.\nUsage: \"mark Index\"");
        }

        index = Integer.parseInt(argsList[1]);
        super.parseArgs(argsList);
    }

}

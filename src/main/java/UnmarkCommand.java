public class UnmarkCommand extends Command {

    private int index;

    @Override
    public void execute(TaskList taskList) throws AuroraException {

        super.execute(taskList);
        Task t = taskList.unmarkTaskDone(index);

        Ui.printMsg("This task has been marked as not done:" + "\n" + t);
        overwriteTaskListFile(taskList);
    }

    @Override
    public void parseArgs(String[] argsList) throws AuroraException {
        // If no arguments provided
        if (argsList.length < 2) {
            throw new AuroraException("Missing argument: Index must be a valid integer value.\nUsage: \"unmark Index\"");

            // Argument provided is not an integer
        } else if (!Parser.canParseInt(argsList[1])) {
            throw new AuroraException("Invalid arguments: Index must be a valid integer value.\nUsage: \"unmark Index\"");
        }

        index = Integer.parseInt(argsList[1]);
        super.parseArgs(argsList);
    }

}

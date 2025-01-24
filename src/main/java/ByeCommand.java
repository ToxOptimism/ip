public class ByeCommand extends Command {

    @Override
    public boolean isExitCommand() {
        return true;
    }

    @Override
    public void execute(TaskList taskList) throws AuroraException {

        super.execute(taskList);

        // Say goodbye to user
        Ui.printMsg("Bye. Hope to see you again soon!");

    }


}

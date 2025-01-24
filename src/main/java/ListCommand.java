public class ListCommand extends Command {

    @Override
    public void execute(TaskList taskList) throws AuroraException {

        super.execute(taskList);

        Ui.displayList(taskList);

    }


}

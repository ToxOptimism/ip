package aurora.command;

import aurora.exception.AuroraException;
import aurora.io.Storage;
import aurora.io.StorageStub;
import aurora.task.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddDeadlineCommandTest extends AddCommand {

    private AddDeadlineCommand addDeadlineCommand;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        addDeadlineCommand = new AddDeadlineCommand();
        taskList = new TaskList();
        storage = StorageStub.of();
    }

    @Test
    public void execute_addValidDeadline_taskAdded() throws AuroraException {
        String[] argsList = {"deadline", "return book /by 2/12/2019 1800"};
        addDeadlineCommand.parseArgs(argsList);
        addDeadlineCommand.execute(taskList, storage);

        assertEquals(1, taskList.getSize());
        assertEquals("[D][ ] return book (by: Dec 2 2019 6:00pm)", taskList.getTask(1).toString());
    }

    @Test
    public void parseArgs_missingDescription_throwsAuroraException() {
        String[] args = {"deadline", "     /by 2/12/2019 1800"};

        AuroraException exception = assertThrows(AuroraException.class, () -> {
            addDeadlineCommand.parseArgs(args);
        });
        assertEquals("Missing argument: \"Description\".\nUsage: \"deadline Description /by By\"", exception.getMessage());
    }

    @Test
    public void parseArgs_missingByDate_throwsAuroraException() {
        String[] args = {"deadline", "return book /by"};

        AuroraException exception = assertThrows(AuroraException.class, () -> {
            addDeadlineCommand.parseArgs(args);
        });
        assertEquals("Missing argument: \"By\" in \"/by By\".\nUsage: \"deadline Description /by By\"", exception.getMessage());
    }

    @Test
    public void parseArgs_missingByIdentifier_throwsAuroraException() {
        String[] args = {"deadline", "return book"};

        AuroraException exception = assertThrows(AuroraException.class, () -> {
            addDeadlineCommand.parseArgs(args);
        });
        assertEquals("Missing argument: \"/by By\".\nUsage: \"deadline Description /by By\"", exception.getMessage());
    }

    @Test
    public void parseArgs_noSpaceByIdentifier_throwsAuroraException() {
        String[] args = {"deadline", "return book 2 /by2/12/2019 1800"};

        AuroraException exception = assertThrows(AuroraException.class, () -> {
            addDeadlineCommand.parseArgs(args);
        });
        assertEquals("Missing argument: \"/by By\".\nUsage: \"deadline Description /by By\"", exception.getMessage());
    }

    @Test
    public void parseArgs_invalidByDate_throwsAuroraException() {
        String[] args = {"deadline", "return book /by notADate"};

        AuroraException exception = assertThrows(AuroraException.class, () -> {
            addDeadlineCommand.parseArgs(args);
        });
        assertEquals("Invalid format: \"By\" must be a valid date format of dd/mm/yyyy hhmm.\nUsage: \"deadline Description /by By\"", exception.getMessage());
    }

}

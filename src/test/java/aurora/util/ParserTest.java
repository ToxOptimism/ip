package aurora.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aurora.command.AddDeadlineCommand;
import aurora.command.AddEventCommand;
import aurora.command.AddToDoCommand;
import aurora.command.ByeCommand;
import aurora.command.Command;
import aurora.command.DeleteCommand;
import aurora.command.FindCommand;
import aurora.command.ListCommand;
import aurora.command.MarkCommand;
import aurora.command.UnmarkCommand;
import aurora.exception.AuroraException;

public class ParserTest {

    private Parser parser;

    @BeforeEach
    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void parseCommand_createByeCommand() throws AuroraException {
        Command command = parser.parseCommand("bye");
        assertInstanceOf(ByeCommand.class, command);
    }

    @Test
    public void parseCommand_createFindCommand() throws AuroraException {
        Command command = parser.parseCommand("find b");
        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    public void parseCommand_createListCommand() throws AuroraException {
        Command command = parser.parseCommand("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    public void parseCommand_markCommand() throws AuroraException {
        Command command = parser.parseCommand("mark 1");
        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void parseCommand_unmarkCommand() throws AuroraException {
        Command command = parser.parseCommand("unmark 1");
        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    public void parseCommand_createToDoCommand() throws AuroraException {
        Command command = parser.parseCommand("todo borrow book");
        assertInstanceOf(AddToDoCommand.class, command);
    }

    @Test
    public void parseCommand_createDeadlineCommand() throws AuroraException {
        Command command = parser.parseCommand("deadline return book /by 2/12/2019 1800");
        assertInstanceOf(AddDeadlineCommand.class, command);
    }

    @Test
    public void parseCommand_createEventCommand() throws AuroraException {
        Command command = parser.parseCommand("event project meeting /from 2/12/2019 1800 /to 4/12/2019 0800");
        assertInstanceOf(AddEventCommand.class, command);
    }

    @Test
    public void parseCommand_createDeleteCommand() throws AuroraException {
        Command command = parser.parseCommand("delete 8");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void parseCommand_createUnknownCommand() {
        AuroraException exception = assertThrows(AuroraException.class, () -> {
            parser.parseCommand("testing unknown command");
        });

        assertEquals("Unknown command: testing unknown command", exception.getMessage());
    }

    @Test
    public void parseDateTime_dateTimeValid() {
        LocalDateTime expected = LocalDateTime.of(2019, 12, 2, 18, 0);
        assertEquals(expected, parser.parseDateTime("2/12/2019 1800"));
    }

    @Test
    public void parseDateTime_notADateTime() {
        // Somewhat trivial
        assertNull(parser.parseDateTime("notADate"));
    }

    @Test
    public void parseDateTime_invalidDivider() {
        // Somewhat trivial
        assertNull(parser.parseDateTime("02-12-2019 1800"));
    }

    @Test
    public void parseDateTime_missingTime() {
        // Somewhat trivial
        assertNull(parser.parseDateTime("02/12/2019"));
    }

    @Test
    public void parseDateTime_missingDate() {
        // Somewhat trivial
        assertNull(parser.parseDateTime("1800"));
    }

    @Test
    public void parseDateTime_emptyString() {
        // Somewhat trivial
        assertNull(parser.parseDateTime(""));
    }
}


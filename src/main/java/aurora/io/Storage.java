package aurora.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import aurora.exception.AuroraException;

/**
 * Represents the storage of the task list data.
 */
public class Storage {

    // Exception messages
    private static final String FILE_CREATE_FAIL =
            "File could not be created.";
    private static final String FILE_READ_FAIL =
            "File could not be read.";
    private static final String FILE_WRITE_FAIL =
            "File could not be written to.";

    // The persistent storage file
    private static Path taskListFile = null;

    // The singleton instance
    private static final Storage SINGLETON = new Storage();

    /**
     * Constructor for Storage.
     */
    protected Storage() {}

    /**
     * Factory method of storage.
     *
     * @return SINGLETON the singleton instance.
     */
    public static Storage of() {
        return SINGLETON;
    }

    /**
     * Creates the task list file if one does not exist.
     * Storage now references the task list file internally.
     *
     * @throws AuroraException if file could not be created.
     */
    public void generateTaskListFile() throws AuroraException {
        Path taskListPath = Paths.get("./", "data", "taskList.txt");
        Path directory = taskListPath.getParent();

        try {
            if (!Files.exists(directory)) {
                Files.createDirectory(taskListPath.getParent());
            }

            if (!Files.exists(taskListPath)) {
                Files.createFile(taskListPath);
            }

            taskListFile = taskListPath;

        } catch (IOException e) {
            throw new AuroraException(FILE_CREATE_FAIL);
        }
    }

    /**
     * Loads the task list data from the file.
     *
     * @return lines the lines of the task list data.
     * @throws AuroraException if file could not be read.
     */
    public List<String> loadTaskListData() throws AuroraException {
        List<String> lines;
        try {
            lines = Files.readAllLines(taskListFile);
        } catch (IOException e) {
            throw new AuroraException(FILE_READ_FAIL);
        }

        return lines;
    }

    /**
     * Overwrites the task list file with new lines of data.
     *
     * @param lines the lines to overwrite the file with.
     * @throws AuroraException if file could not be written to.
     */
    public void overwriteTaskListFile(List<String> lines) throws AuroraException {
        try {
            Files.write(taskListFile, lines);
        } catch (IOException e) {
            throw new AuroraException(FILE_WRITE_FAIL);
        }
    }

    /**
     * Appends new lines of data to the task list file.
     *
     * @param lines the lines to append to the file.
     * @throws AuroraException if file could not be written to.
     */
    public void appendTaskListFile(List<String> lines) throws AuroraException {
        try {
            Files.write(taskListFile, lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new AuroraException(FILE_WRITE_FAIL);
        }
    }
}

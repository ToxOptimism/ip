package aurora.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import aurora.exception.AuroraException;

public class Storage {
    private static Path taskListFile = null;
    private static final Storage SINGLETON = new Storage();

    protected Storage() {}

    public static Storage of() {
        return SINGLETON;
    }

    public void generateTaskListFile() throws AuroraException {
        Path taskListPath = Paths.get("./","data","taskList.txt");
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
            throw new AuroraException("File could not be created.");
        }
    }

    public List<String> loadTaskListData() throws AuroraException {
        List<String> lines;
        try {
            lines = Files.readAllLines(taskListFile);
            return lines;
        } catch (IOException e) {
            throw new AuroraException("File could not be read.");
        }
    }

    public void overwriteTaskListFile(List<String> lines) throws AuroraException {
        try {
            Files.write(taskListFile, lines);
        } catch (IOException e) {
            throw new AuroraException("File could not be written to.");
        }
    }

    public void appendTaskListFile(List<String> lines) throws AuroraException {
        try {
            Files.write(taskListFile, lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new AuroraException("File could not be written to.");
        }
    }
}

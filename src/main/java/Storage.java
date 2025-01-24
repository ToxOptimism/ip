import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static Path taskListFile = null;

    public static void generateTaskListFile() throws AuroraException{
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

    public static List<String> loadTaskListData() throws AuroraException {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(taskListFile);
            return lines;
        } catch (IOException e) {
            throw new AuroraException("File could not be read.");
        }
    }

    public static void overwriteTaskListFile(List<String> lines) throws AuroraException {
        try {
            Files.write(taskListFile, lines);
        } catch (IOException e) {
            throw new AuroraException("File could not be written to.");
        }
    }

    public static void appendTaskListFile(List<String> lines) throws AuroraException {
        try {
            Files.write(taskListFile, lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new AuroraException("File could not be written to.");
        }
    }
}

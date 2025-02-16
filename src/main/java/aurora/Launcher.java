package aurora;

import javafx.application.Application;

/**
 * Represents a launcher class to help workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Aurora.class, args);
    }
}

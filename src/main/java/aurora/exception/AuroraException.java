package aurora.exception;

/**
 * Represents an exception that occurs when there is an error in the aurora package.
 */
public class AuroraException extends Exception {

    /**
     * Constructs a new AuroraException with the specified message.
     *
     * @param message the error message detailing the cause of the exception.
     */
    public AuroraException(String message) {
        super(message);
    }
}

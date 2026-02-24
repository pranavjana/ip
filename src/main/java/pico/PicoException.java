package pico;

/**
 * Represents an exception specific to the Pico application.
 */
public class PicoException extends Exception {

    /**
     * Creates a new PicoException with the given error message.
     *
     * @param message The error message describing the exception.
     */
    public PicoException(String message) {
        super(message);
    }
}

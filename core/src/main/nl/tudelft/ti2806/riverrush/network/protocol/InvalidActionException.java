package nl.tudelft.ti2806.riverrush.network.protocol;

/**
 * Exception thrown when an invalid actions is supplied
 */
public class InvalidActionException extends RuntimeException {
    public InvalidActionException(String message) {
        super(message);
    }
}

package nl.tudelft.ti2806.riverrush.network.protocol;

/**
 * Exception thrown when the protocol is invalid.
 */
public class InvalidProtocolException extends RuntimeException {
    public InvalidProtocolException(String message) {
        super(message);
    }
}

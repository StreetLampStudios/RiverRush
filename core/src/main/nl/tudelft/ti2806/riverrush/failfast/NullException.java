package nl.tudelft.ti2806.riverrush.failfast;

/**
 * Thrown when some assertion yields a null pointer.
 * Different from {@link NullPointerException} as this exception only occurs in checked places.
 */
public final class NullException extends FailFastException {

    public NullException(String message) {
        super(message);
    }

    public NullException() {
        super("No message set.");
    }
}

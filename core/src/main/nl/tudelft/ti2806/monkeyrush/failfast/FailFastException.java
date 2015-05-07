package nl.tudelft.ti2806.monkeyrush.failfast;

/**
 * Base exception for all classes that are thrown in the name of Fail Fast.
 */
public abstract class FailFastException extends RuntimeException {
    public FailFastException(String message) {
        super(message);
    }
}

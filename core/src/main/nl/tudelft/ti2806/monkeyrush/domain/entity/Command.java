package nl.tudelft.ti2806.monkeyrush.domain.entity;

/**
 * Created by thomas on 7-5-15.
 */
@FunctionalInterface
public interface Command {
    void execute();
}

package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.failfast.FailIf;

import java.util.LinkedList;
import java.util.List;

/**
 * An entity that can be observed.
 * Subclasses should call {@code changed()} when the internal state of the class changes.
 * All listed changeHandlers will be fired when this happens.
 */
public abstract class Observable {
    private final List<Command> changeHandlers;

    public Observable() {
        this.changeHandlers = new LinkedList<>();
    }

    public void onChanged(final Command command) {
        FailIf.isNull(command);
        changeHandlers.add(command);
    }

    protected void  changed() {
        changeHandlers.forEach(Command::execute);
    }
}

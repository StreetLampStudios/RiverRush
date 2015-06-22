package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * Abstraction of the Animal state.
 */
public abstract class AbstractAnimalState implements AnimalState {

    /**
     * The event dispatcher of this class.
     */
    private final EventDispatcher dispatcher;

    /**
     * Constructor.
     *
     * @param eventDispatcher - The event disptacher
     */
    public AbstractAnimalState(final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
    }

    protected EventDispatcher getDispatcher() {
        return this.dispatcher;
    }

    @Override
    public void collide() {
        // Does nothing.
    }
}



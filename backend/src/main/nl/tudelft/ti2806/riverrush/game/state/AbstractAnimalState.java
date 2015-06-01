package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * Abstraction of the Animal state.
 */
public abstract class AbstractAnimalState implements AnimalState {

    /**
     * The animal.
     */
    private final AbstractAnimal animal;
    /**
     * The event dispatcher of this class.
     */
    private final EventDispatcher dispatcher;

    /**
     * Constructor.
     *
     * @param aAnimal         - The animal that is on the boat
     * @param eventDispatcher - The event disptacher
     */
    public AbstractAnimalState(final AbstractAnimal aAnimal, final EventDispatcher eventDispatcher) {
        this.animal = aAnimal;
        this.dispatcher = eventDispatcher;
    }

    protected AbstractAnimal getAnimal() {
        return this.animal;
    }

    protected EventDispatcher getDispatcher() {
        return this.dispatcher;
    }
}



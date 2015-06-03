package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * Animal class.
 */
public class Animal extends AbstractAnimal {

    /**
     * Create an animal.
     * @param eventDispatcher - the dispatcher
     */
    public Animal(final EventDispatcher eventDispatcher) {
        super(eventDispatcher);
    }

    /**
     * Try to drop the animal back to the boat.
     */
    public void drop() {
        this.setState(this.getState().drop());
    }
}

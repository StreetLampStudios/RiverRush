package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * Animal class.
 */
public class Animal extends AbstractAnimal {

    /**
     * Create an animal.
     */
    public Animal(EventDispatcher eventDispatcher) {
        super(eventDispatcher);
    }
}

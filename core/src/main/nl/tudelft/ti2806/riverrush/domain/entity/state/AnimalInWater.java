package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.Monkey;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * This state means that the animal is not on the boat.
 */
public class AnimalInWater implements AnimalState {

    private Monkey monkey;
    private final EventDispatcher dispatcher;

    /**
     * Constructor
     * @param monk
     * @param eventDispatcher
     */
    public AnimalInWater(final Monkey monk, final EventDispatcher eventDispatcher) {
        this.monkey = monk;
        this.dispatcher = eventDispatcher;
    }

    @Override
    public AnimalState jump() {
        return this;
    }

    @Override
    public AnimalState drop() {
        return this;
    }

    @Override
    public AnimalState collide() {
        return this;
    }

}

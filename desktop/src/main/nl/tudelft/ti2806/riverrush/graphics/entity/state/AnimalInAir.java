package nl.tudelft.ti2806.riverrush.graphics.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.Animal;

/**
 * State in which the animal is in mid-air. This means the player can't control the animal while in
 * this state.
 */
public class AnimalInAir implements AnimalState {

    /**
     * The animal that is in the air.
     */
    private final Animal animal;

    /**
     * The eventdispatcher of this event.
     */
    private final EventDispatcher dispatcher;

    /**
     * Constructor.
     *
     * @param anim
     *            - The monkey that is in the air
     * @param eventDispatcher
     *            - The event dispatcher of this event
     */
    public AnimalInAir(final Animal anim, final EventDispatcher eventDispatcher) {
        this.animal = anim;
        this.dispatcher = eventDispatcher;
    }

    @Override
    public AnimalState jump() {
        return this;
    }

    @Override
    public AnimalState drop() {
        return new AnimalOnBoat(this.animal, this.dispatcher);
    }

    @Override
    public AnimalState collide() {
        return this;
    }

    @Override
    public AnimalState returnToBoat() {
        return this;
    }
}

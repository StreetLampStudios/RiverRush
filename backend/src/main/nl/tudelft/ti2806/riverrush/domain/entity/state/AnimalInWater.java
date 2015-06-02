package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoatEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * This state means that the animal is not on the boat.
 */
public class AnimalInWater extends AbstractAnimalState {

    /**
     * Constructor.
     *
     * @param eventDispatcher - The event disptacher
     */
    public AnimalInWater(EventDispatcher eventDispatcher) {
        super(eventDispatcher);
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

    @Override
    public AnimalState returnToBoat() {
        this.getDispatcher().dispatch(new AnimalReturnedToBoatEvent());

        return new AnimalOnBoat(this.getDispatcher());
    }

}

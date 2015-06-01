package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * State in which the animal is in mid-air. This means the player can't control the animal while in
 * this state.
 */
public class AnimalInAir extends AbstractAnimalState {
    /**
     * Constructor.
     *
     * @param anAnimal            - The animal that is in the air
     * @param eventDispatcher - The event dispatcher of this event
     */
    public AnimalInAir(final AbstractAnimal anAnimal, final EventDispatcher eventDispatcher) {
        super(anAnimal, eventDispatcher);
    }

    @Override
    public AnimalState jump() {
        return this;
    }

    @Override
    public AnimalState drop() {
        return new AnimalOnBoat(this.getAnimal(), this.getDispatcher());
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

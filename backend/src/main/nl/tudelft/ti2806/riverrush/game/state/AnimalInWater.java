package nl.tudelft.ti2806.riverrush.game.state;

import com.badlogic.gdx.scenes.scene2d.Action;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Monkey;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoatEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * This state means that the animal is not on the boat.
 */
public class AnimalInWater extends AbstractAnimalState {

    /**
     * Constructor.
     *
     * @param aAnimal         - The animal that is on the boat
     * @param eventDispatcher - The event disptacher
     */
    public AnimalInWater(AbstractAnimal aAnimal, EventDispatcher eventDispatcher) {
        super(aAnimal, eventDispatcher);
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

        return new AnimalOnBoat(this.getAnimal(), this.getDispatcher());
    }

}

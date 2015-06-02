package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOffEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * This is the standard state.
 */
public class AnimalOnBoat extends AbstractAnimalState {


    /**
     * Constructor.
     *
     * @param eventDispatcher - The event distpacher
     */
    public AnimalOnBoat(final EventDispatcher eventDispatcher) {
        super(eventDispatcher);
    }

    @Override
    public AnimalState jump() {
        return new AnimalInAir(this.getDispatcher());
    }

    @Override
    public AnimalState drop() {
        return this;
    }

    @Override
    public AnimalState collide() {
        //Action hit = this.getAnimal().collideAction();
        //this.getAnimal().addAction(hit);
        //TODO: add animal and team
        this.getDispatcher().dispatch(new AnimalFellOffEvent());
        return new AnimalInWater(this.getDispatcher());
    }

    @Override
    public AnimalState returnToBoat() {
        return this;
    }
}

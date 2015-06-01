package nl.tudelft.ti2806.riverrush.domain.entity.state;

import com.badlogic.gdx.scenes.scene2d.Action;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOffEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * This is the standard state.
 */
public class AnimalOnBoat extends AbstractAnimalState {


    /**
     * Constructor.
     *
     * @param aAnimal         - The animal that is on the boat
     * @param eventDispatcher - The event distpacher
     */
    public AnimalOnBoat(final AbstractAnimal aAnimal, final EventDispatcher eventDispatcher) {
        super(aAnimal, eventDispatcher);
    }

    @Override
    public AnimalState jump() {
        return new AnimalInAir(this.getAnimal(), this.getDispatcher());
    }

    @Override
    public AnimalState drop() {
        return this;
    }

    @Override
    public AnimalState collide() {
        //Action hit = this.getAnimal().collideAction();
        //this.getAnimal().addAction(hit);
        this.getDispatcher().dispatch(new AnimalFellOffEvent());
        return new AnimalInWater(this.getAnimal(), this.getDispatcher());
    }

    @Override
    public AnimalState returnToBoat() {
        return this;
    }
}

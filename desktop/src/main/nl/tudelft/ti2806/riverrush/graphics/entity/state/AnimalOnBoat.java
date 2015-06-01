package nl.tudelft.ti2806.riverrush.graphics.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOff;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.Animal;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * This is the standard state.
 */
public class AnimalOnBoat implements AnimalState {

    /**
     * The animal.
     */
    private final Animal animal;
    /**
     * The event dispatcher of this class.
     */
    private final EventDispatcher dispatcher;

    /**
     * Constructor.
     *
     * @param anim
     *            - The animal that is on the boat
     * @param eventDispatcher
     *            - The event disptacher
     */
    public AnimalOnBoat(final Animal anim, final EventDispatcher eventDispatcher) {
        this.animal = anim;
        this.dispatcher = eventDispatcher;
    }

    @Override
    public AnimalState jump() {
        Action jump = this.animal.jumpAction();
        this.animal.getActor().addAction(jump);
        return new AnimalInAir(this.animal, this.dispatcher);
    }

    @Override
    public AnimalState drop() {
        return this;
    }

    @Override
    public AnimalState collide() {
        Action hit = this.animal.collideAction();
        this.animal.getActor().addAction(hit);
        this.dispatcher.dispatch(new AnimalFellOff());
        return new AnimalInWater(this.animal, this.dispatcher);
    }

    @Override
    public AnimalState returnToBoat() {
        return this;
    }
}

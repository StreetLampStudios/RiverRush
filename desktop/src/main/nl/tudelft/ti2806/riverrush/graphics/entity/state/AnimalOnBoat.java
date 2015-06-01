package nl.tudelft.ti2806.riverrush.graphics.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOff;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.MonkeyActor;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * This is the standard state.
 */
public class AnimalOnBoat implements AnimalState {

    /**
     * The animal.
     */
    private final MonkeyActor actor;
    /**
     * The event dispatcher of this class.
     */
    private final EventDispatcher dispatcher;

    /**
     * Constructor.
     *
     * @param act
     *            - The animal that is on the boat
     * @param eventDispatcher
     *            - The event disptacher
     */
    public AnimalOnBoat(final MonkeyActor act, final EventDispatcher eventDispatcher) {
        this.actor = act;
        this.dispatcher = eventDispatcher;
    }

    @Override
    public AnimalState jump() {
        Action jump = this.actor.jumpAction();
        this.actor.addAction(jump);
        return new AnimalInAir(this.actor, this.dispatcher);
    }

    @Override
    public AnimalState drop() {
        return this;
    }

    @Override
    public AnimalState collide() {
        Action hit = this.actor.collideAction();
        this.actor.addAction(hit);
        this.dispatcher.dispatch(new AnimalFellOff());
        return new AnimalInWater(this.actor, this.dispatcher);
    }

    @Override
    public AnimalState returnToBoat() {
        return this;
    }
}

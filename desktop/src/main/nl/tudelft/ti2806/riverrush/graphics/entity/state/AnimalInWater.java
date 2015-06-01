package nl.tudelft.ti2806.riverrush.graphics.entity.state;


import com.badlogic.gdx.scenes.scene2d.Action;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoatEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.MonkeyActor;

/**
 * This state means that the animal is not on the boat.
 */
public class AnimalInWater implements AnimalState {

    /**
     * The animal.
     */
    private final MonkeyActor actor;
    /**
     * The dispatcher of this class.
     */
    private final EventDispatcher dispatcher;

    /**
     * Constructor.
     *
     * @param act             - The animal that is in the water
     * @param eventDispatcher - The dispatcher of this event
     */
    public AnimalInWater(final MonkeyActor act, final EventDispatcher eventDispatcher) {
        this.actor = act;
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

    @Override
    public AnimalState returnToBoat() {

        this.dispatcher.dispatch(new AnimalReturnedToBoatEvent());
        Action fade = this.actor.returnFade();
        Action ret = this.actor.returnMove();
        this.actor.addAction(ret);
        this.actor.addAction(fade);

        return new AnimalOnBoat(this.actor, this.dispatcher);
    }

}

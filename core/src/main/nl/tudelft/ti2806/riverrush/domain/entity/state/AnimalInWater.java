package nl.tudelft.ti2806.riverrush.domain.entity.state;

import com.badlogic.gdx.scenes.scene2d.Action;
import nl.tudelft.ti2806.riverrush.domain.entity.Monkey;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoatEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * This state means that the animal is not on the boat.
 */
public class AnimalInWater implements AnimalState {

    /**
     * The animal.
     */
    private final Monkey monkey;
    /**
     * The dispatcher of this class.
     */
    private final EventDispatcher dispatcher;

    /**
     * Constructor.
     *
     * @param monk            - The animal that is in the water
     * @param eventDispatcher - The dispatcher of this event
     */
    public AnimalInWater(final Monkey monk, final EventDispatcher eventDispatcher) {
        this.monkey = monk;
        this.dispatcher = eventDispatcher;

        this.monkey.respawn();
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
        // Action ret = this.monkey.returnAction();
        // this.monkey.addAction(ret);
        Action fade = this.monkey.returnFade();
        Action ret = this.monkey.returnMove();
        this.monkey.addAction(ret);
        this.monkey.addAction(fade);

        return new AnimalOnBoat(this.monkey, this.dispatcher);
    }

}

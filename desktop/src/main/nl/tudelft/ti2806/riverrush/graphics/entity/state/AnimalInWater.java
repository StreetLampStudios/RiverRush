package nl.tudelft.ti2806.riverrush.graphics.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoat;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.Animal;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * This state means that the animal is not on the boat.
 */
public class AnimalInWater implements AnimalState {

    /**
     * The animal.
     */
    private final Animal animal;
    /**
     * The dispatcher of this class.
     */
    private final EventDispatcher dispatcher;

    /**
     * Constructor.
     *
     * @param anim
     *            - The animal that is in the water
     * @param eventDispatcher
     *            - The dispatcher of this event
     */
    public AnimalInWater(final Animal anim, final EventDispatcher eventDispatcher) {
        this.animal = anim;
        this.dispatcher = eventDispatcher;

        this.animal.respawn();
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
        this.dispatcher.dispatch(new AnimalReturnedToBoat());
        // Action ret = this.monkey.returnAction();
        // this.monkey.addAction(ret);
        Action fade = this.animal.returnFade();
        Action ret = this.animal.returnMove();
        this.animal.addAction(ret);
        this.animal.addAction(fade);

        return new AnimalOnBoat(this.animal, this.dispatcher);
    }

}

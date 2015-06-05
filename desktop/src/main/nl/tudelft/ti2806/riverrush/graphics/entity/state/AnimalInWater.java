package nl.tudelft.ti2806.riverrush.graphics.entity.state;


import com.badlogic.gdx.scenes.scene2d.Action;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoatEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.Animal;

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
     * @param anAnimal             - The animal that is in the water
     * @param eventDispatcher - The dispatcher of this event
     */
    public AnimalInWater(final Animal anAnimal, final EventDispatcher eventDispatcher) {
        this.animal = anAnimal;
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
        Action fade = this.animal.getActor().returnFade();
        Action ret = this.animal.getActor().returnMove();
        this.animal.getActor().addAction(ret);
        this.animal.getActor().addAction(fade);

        return new AnimalOnBoat(this.animal, this.dispatcher);
    }

    @Override
    public AnimalState voteDirection(final Direction direction) {
        return this;
    }

}

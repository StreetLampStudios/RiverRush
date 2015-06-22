package nl.tudelft.ti2806.riverrush.graphics.entity.state;

import com.badlogic.gdx.scenes.scene2d.Action;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalCollidedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.Animal;

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
     * @param anAnimal - The animal that is on the boat
     * @param eventDispatcher - The event dispatcher
     */
    public AnimalOnBoat(final Animal anAnimal, final EventDispatcher eventDispatcher) {
        this.animal = anAnimal;
        this.dispatcher = eventDispatcher;
    }

    @Override
    public AnimalState jump() {
        this.animal.getActor().jumpAction();
        return new AnimalInAir(this.animal, this.dispatcher);
    }

    @Override
    public AnimalState drop() {
        return this;
    }

    @Override
    public AnimalState fall() {
        this.animal.getActor().collideAction();
        return new AnimalInWater(this.animal, this.dispatcher);
    }

    @Override
    public AnimalState returnToBoat() {
        return this;
    }

    @Override
    public AnimalState voteDirection(final Direction direction) {
        this.animal.setVoteDirection(direction);
        this.animal.getActor().updateFlag(direction);
        return this;
    }

    @Override
    public void collide() {
        AnimalCollidedEvent event = new AnimalCollidedEvent();
        event.setAnimal(this.animal.getId());
        event.setTeam(this.animal.getTeamId());
        this.dispatcher.dispatch(event);
    }
}

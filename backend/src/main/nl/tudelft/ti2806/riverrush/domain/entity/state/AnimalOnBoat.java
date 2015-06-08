package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOffEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalJumpedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * This is the standard state.
 */
public class AnimalOnBoat extends AbstractAnimalState {

    private Animal animal;

    /**
     * Constructor.
     *
     * @param anim - the animal of the state
     * @param eventDispatcher - The event distpacher
     */
    public AnimalOnBoat(final Animal anim, final EventDispatcher eventDispatcher) {
        super(eventDispatcher);
        this.animal = anim;
    }

    @Override
    public AnimalState jump() {
        AnimalJumpedEvent event = new AnimalJumpedEvent();
        event.setTeam(this.animal.getTeamId());
        event.setAnimal(this.animal.getId());
        this.getDispatcher().dispatch(event);
        return new AnimalInAir(this.animal, this.getDispatcher());
    }

    @Override
    public AnimalState drop() {
        return this;
    }

    @Override
    public AnimalState fall() {
        AnimalFellOffEvent event = new AnimalFellOffEvent();
        event.setTeam(this.animal.getTeamId());
        event.setAnimal(this.animal.getId());
        this.getDispatcher().dispatch(event);
        return new AnimalInWater(this.animal, this.getDispatcher());
    }

    @Override
    public AnimalState returnToBoat() {
        return this;
    }

    @Override
    public AnimalState voteDirection(final Direction direction) {
        this.animal.setVoteDirection(direction);
        return this;
    }
}

package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalInWater;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalOnBoat;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalMovedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * Animal class.
 */
public class Animal extends AbstractAnimal {

    /**
     * Create an animal.
     *
     * @param eventDispatcher - the dispatcher
     */
    public Animal(final EventDispatcher eventDispatcher) {
        super(eventDispatcher);
        this.setState(new AnimalOnBoat(this, eventDispatcher));
    }

    /**
     * Try to drop the animal back to the boat.
     */
    @Override
    public void drop() {
        this.setState(this.getState().drop());
    }

    /**
     * Check if the animal is on the boat.
     *
     * @return true if the animal is on the boat
     */
    public boolean isOnBoat() {
        return this.getState().isOnBoat();
    }

    @Override
    public void setVoteDirection(final Direction direction) {
        super.setVoteDirection(direction);
        AnimalMovedEvent event = new AnimalMovedEvent();
        event.setAnimal(this.getId());
        event.setTeam(this.getTeamId());
        event.setDirection(direction);

        this.getDispatcher().dispatch(event);
    }
}

package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;

/**
 * An abstract implementation of an animal.
 */
public abstract class AbstractAnimal {

    /**
     * The current state of the animal.
     */
    private AnimalState currentState;

    private static final int BITS = 32;
    private static Integer highestId = 0;
    private final Integer animalID;
    private Integer teamID;

    private EventDispatcher dispatcher;

    /**
     * Create an animal.
     */
    public AbstractAnimal(final EventDispatcher dispatch) {
        this.dispatcher = dispatch;
        this.animalID = highestId;
        highestId++;
    }

    /**
     * Create an animal in desktop.
     *
     * @param dispatch - See {@link EventDispatcher}
     * @param animal - Id of the animal
     */
    public AbstractAnimal(final EventDispatcher dispatch, Integer animal) {
        this.dispatcher = dispatch;
        this.animalID = animal;
    }

    /**
     * Get the current state of the animal.
     *
     * @return {@link AnimalState}, never null.
     */
    protected AnimalState getState() {
        return this.currentState;
    }

    /**
     * Set a new state of the anima.
     *
     * @param newState - The new state to set. Fails if null.
     */
    protected void setState(final AnimalState newState) {
        FailIf.isNull(newState);
        this.currentState = newState;
    }

    /**
     * Changes the state to that having been collided.
     */
    public void collide() {
        this.setState(this.getState().collide());
    }

    /**
     * Changes the state to that having jumped.
     */
    public void jump() {
        this.setState(this.getState().jump());
    }

    /**
     * Changes the state to that having returned to the boat.
     */
    public void returnToBoat() {
        this.setState(this.getState().returnToBoat());
    }

    public Integer getId() {
        return this.animalID;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;

        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        AbstractAnimal animal = (AbstractAnimal) o;
        return this.animalID == animal.animalID;

    }

    @Override
    public int hashCode() {
        return this.animalID ^ (this.animalID >>> BITS);
    }

    protected EventDispatcher getDispatcher() {
        return this.dispatcher;
    }

    public Integer getTeamId() {
        return this.teamID;
    }

    /**
     * Sets the team of the animal.
     *
     * @param teamID - id of the team
     */
    public void setTeamId(final Integer teamID) {
        this.teamID = teamID;
    }

}

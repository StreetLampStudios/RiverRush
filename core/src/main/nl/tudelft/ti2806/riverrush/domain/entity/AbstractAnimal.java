package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;

import java.util.Random;

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
    private Direction voteDirection = Direction.NEUTRAL;
    private Sector sectorOnBoat;
    private Integer variation;

    private EventDispatcher dispatcher;

    /**
     * Create an animal.
     */
    public AbstractAnimal(final EventDispatcher dispatch) {
        this.dispatcher = dispatch;
        this.animalID = highestId;
        this.variation = this.getRandomVariation();
        highestId++;
    }

    /**
     * Create an animal in desktop.
     *
     * @param dispatch - See {@link EventDispatcher}
     * @param animal   - Id of the animal
     */
    public AbstractAnimal(final EventDispatcher dispatch, Integer animal) {
        this.dispatcher = dispatch;
        this.animalID = animal;
        this.variation = this.getRandomVariation();
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
    public void fall() {
        this.setState(this.getState().fall());
    }

    /**
     * Changes the state to that having jumped.
     */
    public void jump() {
        this.setState(this.getState().jump());
    }

    /**
     * Changes the state to that being dropped.
     */
    public void drop() {
        this.setState(this.getState().drop());
    }

    /**
     * Changes the state to that having returned to the boat.
     */
    public void returnToBoat() {
        this.setState(this.getState().returnToBoat());
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
        return this.animalID.equals(animal.animalID);

    }

    @Override
    public int hashCode() {
        return this.animalID ^ (this.animalID >>> BITS);
    }

    protected EventDispatcher getDispatcher() {
        return this.dispatcher;
    }

    /**
     * Sets the color of an animal to a random color from an array
     */
    public Integer getRandomVariation() {
        int[] variations = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int idx = new Random().nextInt(variations.length);
        return (variations[idx]);
    }

    public Integer getId() {
        return this.animalID;
    }

    /**
     * Sets the variation of this animal.
     *
     * @param aVariation the variation
     */
    public void setVariation(final Integer aVariation) {
        this.variation = aVariation;
    }

    /**
     * Returns the variation of the animal.
     *
     * @return the variation
     */
    public Integer getVariation() {
        return this.variation;
    }

    /**
     * Sets the team of the animal.
     *
     * @param id - id of the team
     */
    public void setTeamId(final Integer id) {
        this.teamID = id;
    }

    /**
     * @return the direction the animal voted on.
     */
    public Direction getVoteDirection() {
        return this.voteDirection;
    }

    public void voteOneDirection(final Direction direction) {
        this.currentState = this.currentState.voteDirection(direction);
    }

    public void setVoteDirection(final Direction direction) {
        this.voteDirection = direction;
    }

    /**
     * Returns the team ID of the animal.
     *
     * @return the team ID
     */
    public Integer getTeamId() {
        return this.teamID;
    }

    public Sector getSectorOnBoat() {
        return sectorOnBoat;
    }

    public void setSectorOnBoat(Sector sectorOnBoat) {
        this.sectorOnBoat = sectorOnBoat;
    }
}

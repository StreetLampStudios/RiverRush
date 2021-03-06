package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.event.Direction;

/**
 * Represents the state of an animal.
 */
public interface AnimalState {

    /**
     * Animals can jump.
     *
     * @return The new state when an animal jumps.
     */
    AnimalState jump();

    /**
     * Animals drop back down.
     *
     * @return the new state when an animal drops.
     */
    AnimalState drop();

    /**
     * Animal collides with an object.
     *
     * @return the new state when the animal collides.
     */
    AnimalState fall();

    /**
     * Animal returns to the boast.
     *
     * @return the new state after returned to boat.
     */
    AnimalState returnToBoat();

    /**
     * Vote the direction the animal wants to go.
     *
     * @param direction The direction
     * @return The new state after voting
     */
    AnimalState voteDirection(Direction direction);

    /**
     * Collide the animal with an obstacle. Dispatch events when applicable.
     */
    void collide();

    /**
     * Check if the animal is on the boat.
     *
     * @return true if the animal is on the boat
     */
    boolean isOnBoat();
}

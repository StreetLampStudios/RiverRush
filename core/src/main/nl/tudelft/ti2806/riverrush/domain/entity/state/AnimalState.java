package nl.tudelft.ti2806.riverrush.domain.entity.state;

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
  AnimalState collide();

  /**
   * Animal returns to the boast.
   * @return the new state after returned to boat.
   */
  AnimalState returnToBoat();
}

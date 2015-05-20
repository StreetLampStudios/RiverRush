package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.Monkey;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * State in which the animal is in mid-air. This means the player can't control the animal while in
 * this state.
 */
public class AnimalInAir implements AnimalState {

  private Monkey monkey;
  private final EventDispatcher dispatcher;

  public AnimalInAir(Monkey monk, EventDispatcher eventDispatcher) {
    this.monkey = monk;
    this.dispatcher = eventDispatcher;
  }

  @Override
  public AnimalState jump() {
    return this;
  }

  @Override
  public AnimalState drop() {
    return new AnimalOnBoat(this.monkey, this.dispatcher);
  }

  @Override
  public AnimalState collide() {
    return this;
  }
}

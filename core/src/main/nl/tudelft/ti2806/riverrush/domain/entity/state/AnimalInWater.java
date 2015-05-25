package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.Monkey;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoat;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * This state means that the animal is not on the boat.
 */
public class AnimalInWater implements AnimalState {

  private Monkey monkey;
  private final EventDispatcher dispatcher;

  /**
   * Constructor
   *
   * @param monk
   * @param eventDispatcher
   */
  public AnimalInWater(Monkey monk, EventDispatcher eventDispatcher) {
    this.monkey = monk;
    this.dispatcher = eventDispatcher;

    this.monkey.respawn();
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
    Action ret = this.monkey.returnAction();
    this.monkey.addAction(ret);
    return new AnimalOnBoat(this.monkey, this.dispatcher);
  }

}

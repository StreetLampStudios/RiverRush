package nl.tudelft.ti2806.riverrush.domain.entity;

import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2806.riverrush.failfast.FailIf;

/**
 * An abstract implementation of {@link Animal}.
 */
public abstract class AbstractAnimal {
  /**
   * The current state of the animal.
   */
  private AnimalState currentState;

  private static final int RESPAWN_DELAY = 2000;
  private static final int DROP_DELAY = 5000;

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
   * @param newState - THe new state to set. Fails if null.
   */
  protected void setState(final AnimalState newState) {
    FailIf.isNull(newState);
    this.currentState = newState;
  }

  public void leave() {
    // Does nothing yet
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
    Timer tmr = new Timer();
    tmr.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        AbstractAnimal.this.setState(AbstractAnimal.this.getState().drop());
        tmr.cancel();
      }
    }, DROP_DELAY, DROP_DELAY);
  }

  /**
   * Changes the state to that having returned to the boat.
   */
  public void returnToBoat() {
    this.setState(this.getState().returnToBoat());
  }

  /**
   * Respawn the monkey.
   */
  public void respawn() {
    Timer tmr = new Timer();
    tmr.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        AbstractAnimal.this.returnToBoat();
        tmr.cancel();
      }
    }, RESPAWN_DELAY, RESPAWN_DELAY);
  }
}

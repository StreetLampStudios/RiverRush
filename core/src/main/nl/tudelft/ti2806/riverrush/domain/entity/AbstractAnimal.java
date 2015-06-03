package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An abstract implementation of an animal.
 */
public abstract class AbstractAnimal {

    /**
     * The current state of the animal.
     */
    private AnimalState currentState;

    private static final int RESPAWN_DELAY = 2000;
    private static final int DROP_DELAY = 5000;
    private static final int BITS = 32;
    private static Integer highestId = 0;
    private final Integer id;

    private EventDispatcher dispatcher;

    /**
     * Create an animal.
     */
    public AbstractAnimal(final EventDispatcher dispatch) {
        this.dispatcher = dispatch;
        this.id = highestId + 1;
        highestId++;
    }

    /**
     * Create an animal in desktop.
     *
     * @param dispatch
     * @param id
     */
    public AbstractAnimal(final EventDispatcher dispatch, Integer id) {
        this.dispatcher = dispatch;
        this.id = id;
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

    // public void leave() {
    // // Does nothing yet
    // }

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

    public Integer getId() {
        return this.id;
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
        return this.id == animal.id;

    }

    @Override
    public int hashCode() {
        return this.id ^ (this.id >>> BITS);
    }

    protected EventDispatcher getDispatcher() {
        return this.dispatcher;
    }
}

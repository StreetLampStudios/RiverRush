package nl.tudelft.ti2806.riverrush.domain.entity;

import com.badlogic.gdx.scenes.scene2d.Actor;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;
import nl.tudelft.ti2806.riverrush.failfast.FailIf;

/**
 * An abstract implementation of {@link Animal}.
 */
public abstract class AbstractAnimal extends Actor implements Animal {
    /**
     * The current state of the animal.
     */
    private AnimalState currentState;

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
}

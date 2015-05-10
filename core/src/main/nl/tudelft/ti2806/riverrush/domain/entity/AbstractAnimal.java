package nl.tudelft.ti2806.riverrush.domain.entity;

import nl.tudelft.ti2806.riverrush.state.AnimalOnBoat;
import nl.tudelft.ti2806.riverrush.state.AnimalState;

/**
 * Created by thomas on 8-5-15.
 */
public abstract class AbstractAnimal implements Animal {
    protected AnimalState currentState;

    public AbstractAnimal() {
        this.currentState = new AnimalOnBoat();
    }

    @Override
    public void jump() {
        this.currentState = this.currentState.jump();
    }

    @Override
    public void leave() {

    }
}

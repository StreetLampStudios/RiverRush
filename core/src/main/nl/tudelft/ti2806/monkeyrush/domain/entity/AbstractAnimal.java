package nl.tudelft.ti2806.monkeyrush.domain.entity;

import nl.tudelft.ti2806.monkeyrush.state.AnimalOnBoat;
import nl.tudelft.ti2806.monkeyrush.state.AnimalState;

/**
 * Created by thomas on 8-5-15.
 */
public abstract class AbstractAnimal extends Observable implements Animal {
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

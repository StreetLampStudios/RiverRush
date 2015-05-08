package nl.tudelft.ti2806.monkeyrush.entities;

import nl.tudelft.ti2806.monkeyrush.state.AnimalOnBoat;
import nl.tudelft.ti2806.monkeyrush.state.AnimalState;

/**
 * Created by thomas on 7-5-15.
 */
public class Player extends Observable {
    private AnimalState currentState;

    public Player() {
        currentState = new AnimalOnBoat();
    }

    public void jump() {
        changed();
    }

    public void leaveGame() {

    }
}

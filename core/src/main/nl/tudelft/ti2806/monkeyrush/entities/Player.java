package nl.tudelft.ti2806.monkeyrush.entities;

import nl.tudelft.ti2806.monkeyrush.entities.states.PlayerOnBoat;
import nl.tudelft.ti2806.monkeyrush.entities.states.PlayerState;

/**
 * Created by thomas on 7-5-15.
 */
public class Player extends Observable {
    private PlayerState currentState;

    public Player() {
        currentState = new PlayerOnBoat();
    }

    public void jump() {
        changed();
    }

    public void leaveGame() {

    }
}

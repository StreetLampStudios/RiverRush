package nl.tudelft.ti2806.riverrush.backend;

import nl.tudelft.ti2806.riverrush.network.Server;
import nl.tudelft.ti2806.riverrush.state.GameState;
import nl.tudelft.ti2806.riverrush.state.WaitingGameState;

/**
 * Main backend application responsible for starting and stopping the game.
 */
public class Application {

    private GameState gameState;
    private Server  server;

    public Application(int portNumber) {
        this.gameState = new WaitingGameState();
        //this.server = new Server(portNumber);
    }

    public void start() {

    }

    public void stop() {

    }
}

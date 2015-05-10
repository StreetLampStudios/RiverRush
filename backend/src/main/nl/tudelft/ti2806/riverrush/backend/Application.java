package nl.tudelft.ti2806.riverrush.backend;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.CoreModule;
import nl.tudelft.ti2806.riverrush.network.Server;
import nl.tudelft.ti2806.riverrush.state.GameState;
import nl.tudelft.ti2806.riverrush.state.WaitingGameState;

/**
 * Main backend application responsible for starting and stopping the game.
 */
public class Application extends AbstractModule {

    private GameState gameState;
    private Server server;

    public Application(final int portNumber) {
        this.gameState = new WaitingGameState();
        Injector injector = Guice.createInjector(new CoreModule(), this);
        this.server = injector.getInstance(Server.class);
    }

    public void start() {

    }

    public void stop() {

    }

    @Override
    protected void configure() {
        bind(Server.class);
    }
}

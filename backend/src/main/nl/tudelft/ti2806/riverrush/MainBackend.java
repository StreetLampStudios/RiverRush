package nl.tudelft.ti2806.riverrush;

import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.domain.entity.game.Game;
import nl.tudelft.ti2806.riverrush.domain.event.BasicEventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.Server;

/**
 * Entrypoint of the backend.
 */
public final class MainBackend extends CoreModule {
    /**
     * A {@link Server} that fires NetworkEvents for listeners to dispatch.
     */
    private Server renderServer;

    private Server clientServer;

    private Game game;
    private final Injector injector;

    /**
     * Main is a utility class.
     */
    private MainBackend() {
        injector = Guice.createInjector(this);

        this.game = injector.getInstance(Game.class);

        this.renderServer = new Server(
            injector.getInstance(EventDispatcher.class),
            this.configureRendererProtocol());

        this.clientServer = new Server(
            injector.getInstance(EventDispatcher.class),
            this.configureClientProtocol());

        this.clientServer.start();
        this.renderServer.start();
    }

    public static void main(final String[] args) {
        new MainBackend();
    }


    @Override
    protected EventDispatcher configureEventDispatcher() {
        EventDispatcher dispatcher = new BasicEventDispatcher();


        return dispatcher;
    }
}

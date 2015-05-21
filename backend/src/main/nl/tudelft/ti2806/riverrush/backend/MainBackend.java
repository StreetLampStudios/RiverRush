package nl.tudelft.ti2806.riverrush.backend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.CoreModule;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.controller.PlayerController;
import nl.tudelft.ti2806.riverrush.controller.RenderController;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.RenderServer;
import nl.tudelft.ti2806.riverrush.network.Server;
import nl.tudelft.ti2806.riverrush.network.UserServer;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import static com.google.inject.name.Names.named;

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

        this.renderServer = injector.getInstance(RenderServer.class);
        this.clientServer = injector.getInstance(UserServer.class);

        this.clientServer.start();
        this.renderServer.start();
    }

    public static void main(final String[] args) {
        new MainBackend();
    }

    @Override
    protected void configure() {
        super.configure();
        this.bind(Controller.class)
            .annotatedWith(named("clientController"))
            .to(PlayerController.class);

        this.bind(Controller.class)
            .annotatedWith(named("renderController"))
            .to(RenderController.class);

        this.bind(Protocol.class)
            .annotatedWith(named("clientProtocol"))
            .toInstance(this.configureClientProtocol());

        this.bind(Protocol.class)
            .annotatedWith(named("renderProtocol"))
            .toInstance(this.configureRendererProtocol());
    }
}

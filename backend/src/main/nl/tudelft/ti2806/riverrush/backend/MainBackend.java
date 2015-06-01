package nl.tudelft.ti2806.riverrush.backend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.CoreModule;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.controller.UserController;
import nl.tudelft.ti2806.riverrush.controller.RenderController;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.AbstractServer;
import nl.tudelft.ti2806.riverrush.network.RenderServer;
import nl.tudelft.ti2806.riverrush.network.UserServer;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import static com.google.inject.name.Names.named;

/**
 * Entrypoint of the backend.
 */
public final class MainBackend extends CoreModule {

    /**
     * Main is a utility class.
     */
    private MainBackend() {
        Injector injector = Guice.createInjector(this);
        injector.getInstance(Game.class);

        AbstractServer renderServer = injector.getInstance(RenderServer.class);
        AbstractServer clientServer = injector.getInstance(UserServer.class);

        clientServer.start();
        renderServer.start();
    }

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments are ignored.
     */
    public static void main(final String[] args) {
        new MainBackend();
    }

    @Override
    protected void configure() {
        super.configure();
        this.bind(Controller.class)
            .annotatedWith(named("clientController"))
            .to(UserController.class);

        this.bind(Controller.class)
            .annotatedWith(named("renderController"))
            .to(RenderController.class);

        this.bind(Protocol.class)
            .annotatedWith(named("clientProtocol"))
            .toInstance(this.configureClientProtocol());

        this.bind(Protocol.class)
            .annotatedWith(named("renderProtocol"))
            .toInstance(this.configureRendererProtocol());

        this.bind(AbstractServer.class)
            .annotatedWith(named("playerServer"))
            .to(UserServer.class);

        this.bind(AbstractServer.class)
            .annotatedWith(named("renderServer"))
            .to(RenderServer.class);
    }
}

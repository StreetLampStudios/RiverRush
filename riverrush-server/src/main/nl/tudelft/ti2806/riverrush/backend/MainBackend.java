package nl.tudelft.ti2806.riverrush.backend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import nl.tudelft.ti2806.riverrush.CoreModule;
import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.controller.RenderController;
import nl.tudelft.ti2806.riverrush.controller.UserController;
import nl.tudelft.ti2806.riverrush.domain.event.AbstractTeamEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameWaitingEvent;
import nl.tudelft.ti2806.riverrush.domain.event.HandlerLambda;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.game.LevelMapParser;
import nl.tudelft.ti2806.riverrush.network.AbstractServer;
import nl.tudelft.ti2806.riverrush.network.RenderServer;
import nl.tudelft.ti2806.riverrush.network.UserServer;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.TreeMap;

import static com.google.inject.name.Names.named;

/**
 * Entry point of the backend.
 */
public final class MainBackend extends CoreModule {

    private HandlerLambda<GameWaitingEvent> handler;
    private final AbstractServer clientServer;

    /**
     * Main is a utility class.
     */
    private MainBackend() {
        LogManager.getLogger(MainBackend.class).info("Starting server...");
        Injector injector = Guice.createInjector(this);
        injector.getInstance(Game.class);

        AbstractServer renderServer = injector.getInstance(RenderServer.class);
        clientServer = injector.getInstance(UserServer.class);

        renderServer.start();


        EventDispatcher dispatcher = injector.getInstance(EventDispatcher.class);
        handler = (e) -> this.startClientServer(dispatcher);
        dispatcher.attach(GameWaitingEvent.class, handler);
    }

    private void startClientServer(final EventDispatcher d) {
        d.detach(GameWaitingEvent.class, handler);
        clientServer.start();
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
        this.bind(new TypeLiteral<TreeMap<Double, AbstractTeamEvent>>() {}).annotatedWith(named("levelMap")).toInstance(this.configureLevelMap());

        this.bind(Controller.class).annotatedWith(named("clientController"))
            .to(UserController.class);

        this.bind(Controller.class).annotatedWith(named("renderController"))
            .to(RenderController.class);

        this.bind(Protocol.class).annotatedWith(named("clientProtocol"))
            .toInstance(this.configureClientProtocol());

        this.bind(Protocol.class).annotatedWith(named("renderProtocol"))
            .toInstance(this.configureRendererProtocol());

        this.bind(AbstractServer.class).annotatedWith(named("playerServer")).to(UserServer.class);

        this.bind(AbstractServer.class).annotatedWith(named("renderServer")).to(RenderServer.class);
    }

    /**
     * Configure the level map.
     *
     * @return The level map
     */
    public static TreeMap<Double, AbstractTeamEvent> configureLevelMap() {
        try {
            return LevelMapParser.readFromFile("/simpletrack.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

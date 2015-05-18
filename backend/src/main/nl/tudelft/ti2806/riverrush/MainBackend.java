package nl.tudelft.ti2806.riverrush;

import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.domain.entity.game.Game;
import nl.tudelft.ti2806.riverrush.domain.event.BasicEventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.listener.EventListener;
import nl.tudelft.ti2806.riverrush.domain.event.listener.SendEventListener;
import nl.tudelft.ti2806.riverrush.network.Server;
import org.reflections.Reflections;

import java.util.Set;

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
            injector.getProvider(EventDispatcher.class),
            this.configureRendererProtocol(),
            injector.getInstance(SendEventListener.class));

        this.clientServer = new Server(
            injector.getProvider(EventDispatcher.class),
            this.configureClientProtocol(),
            injector.getInstance(SendEventListener.class));
    }

    public static void main(final String[] args) {
        new MainBackend();
    }


    @Override
    protected EventDispatcher configureEventDispatcher() {
        EventDispatcher dispatcher = new BasicEventDispatcher();

        try {
            registerAllEventListeners(dispatcher);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return dispatcher;
    }

    /**
     * Uses reflection to find all subclasses of {@link EventListener <?>}
     * Registers an instance of each to any event dispatcher created on runtime.
     * @param dispatcher
     */
    private void registerAllEventListeners(EventDispatcher dispatcher) throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(getClass().getPackage().getName());
        Set<Class<? extends EventListener>> classes = reflections.getSubTypesOf(EventListener.class);

        for (Class<? extends EventListener> clasz : classes) {
            EventListener listener = injector.getInstance(clasz);
            dispatcher.register(listener.getEventType(), listener);
        }
    }
}

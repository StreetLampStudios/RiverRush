package nl.tudelft.ti2806.riverrush;

import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.backend.eventlisteners.JoinEventListener;
import nl.tudelft.ti2806.riverrush.domain.entity.Game;
import nl.tudelft.ti2806.riverrush.domain.event.BasicEventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventListener;
import nl.tudelft.ti2806.riverrush.network.Server;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;

/**
 * Entrypoint of the backend.
 */
public final class MainBackend extends CoreModule {
    /**
     * A {@link Server} that fires NetworkEvents for listeners to dispatch.
     */
    private static Server renderServer;

    private static Server clientServer;

    private static Game game;

    /**
     * Main is a utility class.
     */
    private MainBackend() { }

    public static void main(final String[] args) {
        Injector injector = Guice.createInjector(new MainBackend());

        game = injector.getInstance(Game.class);

        renderServer = injector.getInstance(Server.class);
        clientServer = injector.getInstance(Server.class);
    }

    private static final EventListener<JoinEvent> joinListener = new JoinEventListener();
    @Override
    protected EventDispatcher configureEventDispatcher() {
        EventDispatcher dispatcher = new BasicEventDispatcher();
        dispatcher.register(JoinEvent.class, joinListener);
        return dispatcher;
    }
}

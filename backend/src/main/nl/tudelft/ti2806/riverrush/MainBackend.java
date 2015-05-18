package nl.tudelft.ti2806.riverrush;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import nl.tudelft.ti2806.riverrush.backend.eventlisteners.JoinEventListener;
import nl.tudelft.ti2806.riverrush.domain.entity.Game;
import nl.tudelft.ti2806.riverrush.domain.event.BasicEventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventListener;
import nl.tudelft.ti2806.riverrush.network.Server;
import nl.tudelft.ti2806.riverrush.network.event.JoinEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

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

    /**
     * Main is a utility class.
     */
    private MainBackend() {
        Injector injector = Guice.createInjector(this);

        this.game = injector.getInstance(Game.class);

        this.renderServer = new Server(injector.getProvider(EventDispatcher.class), this.configureRendererProtocol());
        this.clientServer = new Server(injector.getProvider(EventDispatcher.class), this.configureClientProtocol());
    }

    public static void main(final String[] args) {
        MainBackend mainBackend = new MainBackend();
    }

    private static final EventListener<JoinEvent> joinListener = new JoinEventListener();
    @Override
    protected EventDispatcher configureEventDispatcher() {
        EventDispatcher dispatcher = new BasicEventDispatcher();
        dispatcher.register(JoinEvent.class, joinListener);
        return dispatcher;
    }
}

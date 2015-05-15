package nl.tudelft.ti2806.riverrush.network;

import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.CoreModule;
import nl.tudelft.ti2806.riverrush.backend.eventlisteners.EchoNetworkEventListener;
import nl.tudelft.ti2806.riverrush.domain.event.BasicEventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.EventListener;
import nl.tudelft.ti2806.riverrush.network.event.EchoNetworkEvent;
import nl.tudelft.ti2806.riverrush.network.protocol.BasicProtocol;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * Created by thomas on 13-5-15.
 */
public class EchoServer extends CoreModule{
    /**
     * A {@link Server} that fires NetworkEvents for listeners to dispatch.
     */
    private static Server server;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new EchoServer());
        server = injector.getInstance(Server.class);
        server.start();
    }

    private static final EventListener<EchoNetworkEvent> joinListener = new EchoNetworkEventListener();
    @Override
    protected EventDispatcher configureEventDispatcher() {
        EventDispatcher d = new BasicEventDispatcher();
        d.register(EchoNetworkEvent.class, joinListener);
        return d;
    }

    @Override
    protected Protocol configureProtocol() {
        Protocol protocol = BasicProtocol.getInstance();
        // Register available network actions
        // protocol.registerNetworkAction(...);
        protocol.registerNetworkAction(EchoNetworkEvent.class, EchoNetworkEvent::new);

        return protocol;
    }
}

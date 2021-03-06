package nl.tudelft.ti2806.riverrush.network;

import nl.tudelft.ti2806.riverrush.controller.Controller;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import static com.google.inject.name.Names.named;

/**
 * Created by thomas on 21-5-15.
 */
public class RenderServerTest extends ServerTest {

    /**
     * Configures injection of mocks.
     */
    @Override
    protected void configure() {
        // Every time a new EventDispatcher is requested by code under test,
        // Guice will inject a fresh mock.
        this.bind(EventDispatcher.class).toInstance(dispatcherMock);

        this.bind(Controller.class)
                .annotatedWith(named("renderController"))
                .toProvider(this.controllerProviderMock);

        this.bind(Protocol.class)
                .annotatedWith(named("renderProtocol"))
                .toInstance(this.protocolMock);

        this.bind(AbstractServer.class).to(RenderServer.class);
    }


}

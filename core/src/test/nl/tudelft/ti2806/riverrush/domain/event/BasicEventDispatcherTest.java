package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link BasicEventDispatcher}.
 */
public class BasicEventDispatcherTest {

    /**
     * Class under test.
     */
    private EventDispatcher dispatcher;

    @Mock
    private HandlerLambda<Event> lambdaMock;

    @Mock
    private Event eventMock;

    /**
     * Setup.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.dispatcher = new BasicEventDispatcher();
    }

    /**
     * attach should add the event type and lsitener.
     */
    @Test
    public void registerAddsListener1() {
        this.dispatcher.attach(Event.class, this.lambdaMock);
        assertEquals(1, this.dispatcher.countRegistered(Event.class));
    }

    @Test
    public void registerAddsListener2() {
        this.dispatcher.attach(Event.class, this.lambdaMock);
        this.dispatcher.attach(Event.class, this.lambdaMock);
        assertEquals(2, this.dispatcher.countRegistered(Event.class));
    }

    @Test
    public void countRegistered() {
        assertEquals(0, this.dispatcher.countRegistered(Event.class));
    }

    @Test
    public void dispatch_callsListener() {
        this.dispatcher.attach((Class<Event>) this.eventMock.getClass(), this.lambdaMock);
        this.dispatcher.dispatch(this.eventMock);
        verify(this.lambdaMock).handle(this.eventMock);
    }

    @Test
    public void dispatch_callsAllListeners() {
        this.dispatcher.attach((Class<Event>) this.eventMock.getClass(), this.lambdaMock);
        this.dispatcher.attach((Class<Event>) this.eventMock.getClass(), this.lambdaMock);
        this.dispatcher.dispatch(this.eventMock);
        verify(this.lambdaMock, Mockito.times(2)).handle(this.eventMock);
    }

    @Test
    public void dispatch_callsCorrectListener() {
        HandlerLambda dummyListener = mock(HandlerLambda.class);

        this.dispatcher.attach(DummyEvent.class, dummyListener);
        this.dispatcher.dispatch(this.eventMock);
        verifyZeroInteractions(this.lambdaMock);
    }

    private class DummyEvent implements Event {

        @Override
        public String serialize(final Protocol protocol) {
            return "";
        }

        @Override
        public Event deserialize(final Map<String, String> keyValuePairs) {
            return this;
        }
    }
}

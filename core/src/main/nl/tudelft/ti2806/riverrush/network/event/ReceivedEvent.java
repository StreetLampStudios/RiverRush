package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Created by thomas on 18-5-15.
 */
public class ReceivedEvent implements Event {

    private final Event wrappedEvent;

    private final InetSocketAddress address;


    public ReceivedEvent(Event wrappedEvent, InetSocketAddress remoteAddress) {
        this.wrappedEvent = wrappedEvent;
        this.address = remoteAddress;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    @Override
    public String serialize(Protocol protocol) {
        return this.wrappedEvent.serialize(protocol);
    }

    @Override
    public Event deserialize(Map<String, String> keyValuePairs) {
        return this.wrappedEvent.deserialize(keyValuePairs);
    }

    @Override
    public void setPlayer(Player p) {

    }

    @Override
    public Player getPlayer() {
        return null;
    }
}


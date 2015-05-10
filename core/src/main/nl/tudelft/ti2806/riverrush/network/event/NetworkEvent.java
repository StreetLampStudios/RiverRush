package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.domain.event.Event;

import java.util.Map;


public interface NetworkEvent extends Event {
    String serialize();
    NetworkEvent deserialize(Map<String, String> keyValuePairs);
}

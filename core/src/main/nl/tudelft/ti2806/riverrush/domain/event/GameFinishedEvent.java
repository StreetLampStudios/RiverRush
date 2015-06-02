package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Event raised when one of the teams wins.
 */
public class GameFinishedEvent implements Event {

    @Override
    public String serialize(final Protocol protocol) {
        return "";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    @Override
    public void setAnimal(final Integer aAnimal) {
        // Has to be empty
    }

    @Override
    public Integer getAnimal() {
        // Has to be empty
        return null;
    }
}

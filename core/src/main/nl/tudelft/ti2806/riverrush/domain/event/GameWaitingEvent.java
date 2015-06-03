package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Event raised when the game is waiting for players to join.
 */
public class GameWaitingEvent implements Event {

    @Override
    public String serialize(final Protocol protocol) {
        return "";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    @Override
    public Integer getAnimal() {
        return null;
    }

    @Override
    public void setAnimal(final Integer aAnimal) {
        // Has to be empty
    }
}

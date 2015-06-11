package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Event raised when the game is waiting for players to join.
 */
public class GameWaitingEvent extends AbstractAnimalEvent {

    @Override
    public String serialize(final Protocol protocol) {
        return super.serialize(protocol);
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return super.deserialize(keyValuePairs);
    }
}

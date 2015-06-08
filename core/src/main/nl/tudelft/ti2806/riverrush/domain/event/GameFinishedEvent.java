package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Event raised when one of the teams wins.
 */
public class GameFinishedEvent extends AbstractTeamEvent {

    @Override
    public String serialize(final Protocol protocol) {
        return super.serialize(protocol);
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return super.deserialize(keyValuePairs);
    }
}

package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Domain event for a jumping player.
 */
public class AnimalJumpedEvent extends AbstractTeamAnimalEvent {

    @Override
    public String serialize(final Protocol protocol) {
        return super.serialize(protocol);
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return super.deserialize(keyValuePairs);
    }
}

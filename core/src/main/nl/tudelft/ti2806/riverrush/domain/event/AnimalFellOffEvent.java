package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This is the event that is sent from the server to the device and the renderer to say that the animal
 * has fallen off the boat.
 */
public class AnimalFellOffEvent extends AbstractTeamAnimalEvent {

    @Override
    public String serialize(final Protocol protocol) {
        return super.serialize(protocol);
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return super.deserialize(keyValuePairs);
    }
}

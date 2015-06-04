package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This event is sent from the device to the server.
 */
public class JumpCommand implements Event {

    private Integer animalId;

    @Override
    public String serialize(final Protocol protocol) {
        return "";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    public Integer getAnimal() {
        return this.animalId;
    }

    public void setAnimal(final Integer aAnimal) {
        this.animalId = aAnimal;
    }
}

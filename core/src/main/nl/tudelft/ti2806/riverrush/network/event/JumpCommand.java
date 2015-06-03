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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("animal").append(protocol.getKeyValueSeperator()).append(this.animalId.toString());
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.containsKey("animal")) {
            this.animalId = Integer.parseInt(keyValuePairs.get("animal"));
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    public Integer getAnimal() {
        return this.animalId;
    }

    public void setAnimal(final Integer aAnimal) {
        this.animalId = aAnimal;
    }
}

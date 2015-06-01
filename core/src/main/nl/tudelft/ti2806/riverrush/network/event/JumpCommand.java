package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This event is sent from the device to the server.
 */
public class JumpCommand implements Event {


    private Integer id;

    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een CollidedEvent]";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    public void setAnimal(final AbstractAnimal aAnimal) {
        this.id = aAnimal.getId();
    }

    public Integer getAnimal() {
        return this.id;
    }
}

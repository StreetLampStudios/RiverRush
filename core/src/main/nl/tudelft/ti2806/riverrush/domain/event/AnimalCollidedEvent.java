package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This is the event that is sent from the renderer to the server to say that the animal
 * has collided.
 */
public class AnimalCollidedEvent implements Event {

    private Integer id;

    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een CollidedEvent]";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    @Override
    public void setAnimal(final AbstractAnimal aAnimal) {
        this.id = aAnimal.getId();
    }

    @Override
    public Integer getAnimal() {
        return this.id;
    }
}

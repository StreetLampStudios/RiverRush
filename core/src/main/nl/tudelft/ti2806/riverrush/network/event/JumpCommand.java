package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This event is sent from the device to the server.
 */
public class JumpCommand implements Event {


    private Animal animal;

    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een CollidedEvent]";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    public void setAnimal(final Animal aAnimal) {
        this.animal = aAnimal;
    }

    public Animal getAnimal() {
        return this.animal;
    }
}

package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Domain event for when a player is added to the game.
 */
public class AnimalAddedEvent implements Event {

    private Animal animal;

    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een CollidedEvent]";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    @Override
    public void setAnimal(Animal aAnimal) {
        this.animal = aAnimal;
    }

    @Override
    public Animal getAnimal() {
        return this.animal;
    }
}

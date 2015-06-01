package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * The state where the animal gets back up on the boat after being knocked into the water.
 */
public class AnimalReturnedToBoatEvent implements Event {

    private Animal animal;

    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een FallOffEvent]";
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

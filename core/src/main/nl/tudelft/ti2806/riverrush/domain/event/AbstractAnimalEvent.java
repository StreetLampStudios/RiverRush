package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Abstract class for all duplicate code of all events.
 */
public abstract class AbstractAnimalEvent implements Event {

    @Override
    public String serialize(final Protocol protocol) {
        return "";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    @Override
    public Integer getAnimal() {
        return -1;
    }

    @Override
    public void setAnimal(final Integer aAnimal) {
        // Has to be empty
    }
}

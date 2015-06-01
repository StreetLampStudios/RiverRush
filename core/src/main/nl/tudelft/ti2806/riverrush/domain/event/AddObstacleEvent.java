package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This event is fired when an obstacle needs to be added to the screen.
 */
public class AddObstacleEvent implements Event {

    private boolean isLeft = false; //TODO: rename
    private double location;

    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een ObstacleEvent]";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.get("isLeft").equals("true")) {
            isLeft = true;
        }
        location = Double.parseDouble(keyValuePairs.get("location"));
        return this;
    }

    @Override
    public void setAnimal(AbstractAnimal animal) {
        // Has to be empty
    }

    @Override
    public Integer getAnimal() {
        return 0;
    }

    public double getLocation() {
        return location;
    }

    public boolean isLeft() {
        return isLeft;
    }
}

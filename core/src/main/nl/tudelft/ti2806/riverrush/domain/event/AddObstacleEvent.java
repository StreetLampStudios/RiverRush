package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This event is fired when an obstacle needs to be added to the screen.
 */
public class AddObstacleEvent implements Event {

    private boolean isLeft = false;
    private double offset;

    @Override
    public String serialize(final Protocol protocol) {
        //TODO add protocol string
        return "";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.get("isLeft").equals("true")) {
            isLeft = true;
        }
        offset = Double.parseDouble(keyValuePairs.get("offset"));
        return this;
    }

    public double getOffset() {
        return offset;
    }

    public boolean isLeft() {
        return isLeft;
    }
}

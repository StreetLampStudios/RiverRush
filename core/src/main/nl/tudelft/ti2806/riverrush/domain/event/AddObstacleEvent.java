package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This event is fired when an obstacle needs to be added to the screen.
 */
public class AddObstacleEvent extends AbstractTeamEvent {

    private Double location;

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.serialize(protocol));
        stringBuilder.append(protocol.getPairSeperator());
        stringBuilder.append("location").append(protocol.getKeyValueSeperator())
            .append(this.location.toString());
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        super.deserialize(keyValuePairs);
        if (keyValuePairs.containsKey("location")) {
            this.location = Double.parseDouble(keyValuePairs.get("location"));
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    public double getLocation() {
        return this.location;
    }

    public void setLocation(final Double aLocation) {
        this.location = aLocation;
    }

    @Override
    public String toString() {
        return "AddObstacleEvent{"
            + "location=" + location
            + "} " + super.toString();
    }
}

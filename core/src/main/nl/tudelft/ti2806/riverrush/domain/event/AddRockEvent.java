package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This event is fired when an obstacle needs to be added to the screen.
 */
public class AddRockEvent extends AbstractTeamEvent {

    private Direction location;

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
            this.location = Direction.valueOf(keyValuePairs.get("location").toUpperCase());
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    public Direction getLocation() {
        return this.location;
    }

    public void setLocation(final Direction aLocation) {
        this.location = aLocation;
    }

    @Override
    public String toString() {
        return "AddRockEvent{"
                + "location=" + location
                + "} " + super.toString();
    }
}

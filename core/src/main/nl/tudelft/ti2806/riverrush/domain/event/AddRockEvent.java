package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This event is fired when an obstacle needs to be added to the screen.
 */
public class AddRockEvent implements Event {

    private Direction location;
    private Integer teamId;

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("team").append(protocol.getKeyValueSeperator())
            .append(this.teamId.toString());
        stringBuilder.append("location").append(protocol.getKeyValueSeperator())
            .append(this.location.toString());
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.containsKey("team") && keyValuePairs.containsKey("location")) {
            this.teamId = Integer.parseInt(keyValuePairs.get("team"));
            this.location = Direction.valueOf(keyValuePairs.get("location").toUpperCase());
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    @Override
    public Integer getAnimal() {
        return 0;
    }

    @Override
    public void setAnimal(final Integer animal) {
        // Has to be empty
    }

    public Direction getLocation() {
        return this.location;
    }

    public void setLocation(Direction aLocation) {
        this.location = aLocation;
    }

    public Integer getTeam() {
        return this.teamId;
    }

    public void setTeam(final Integer team) {
        this.teamId = team;
    }
}

package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * This event is fired when an obstacle needs to be added to the screen.
 */
public class AddRockEvent implements Event {

    private Double location;
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
            this.location = Double.parseDouble(keyValuePairs.get("location"));
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

    public double getLocation() {
        return this.location;
    }

    public void setLocation(Double aLocation) {
        this.location = aLocation;
    }

    public Integer getTeam() {
        return this.teamId;
    }

    public void setTeam(final Integer team) {
        this.teamId = team;
    }
}

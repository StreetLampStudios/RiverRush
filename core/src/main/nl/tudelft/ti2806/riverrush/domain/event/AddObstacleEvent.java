package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This event is fired when an obstacle needs to be added to the screen.
 */
public class AddObstacleEvent implements Event {

    private Double location;
    private Integer teamId;

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("team").append(protocol.getKeyValueSeperator()).append(this.teamId.toString());
        stringBuilder.append("location").append(protocol.getKeyValueSeperator()).append(this.location.toString());
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

    public Integer getTeam() {
        return this.teamId;
    }

    public void setTeam(AbstractTeam team) {
        this.teamId = team.getId();
    }
}

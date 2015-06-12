package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Abstract class for all duplicate code of all events containing team.
 */
public abstract class AbstractTeamEvent implements Event {

    private Integer teamId;

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("team").append(protocol.getKeyValueSeperator()).append(this.teamId.toString());
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.containsKey("team")) {
            this.teamId = Integer.parseInt(keyValuePairs.get("team"));
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
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

    public Integer getTeam() {
        return this.teamId;
    }

    public void setTeam(final Integer team) {
        this.teamId = team;
    }

    @Override
    public String toString() {
        return "AbstractTeamEvent{" +
            "teamId=" + teamId +
            '}';
    }
}

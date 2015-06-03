package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Event raised when one of the teams wins.
 */
public class GameFinishedEvent implements Event {

    private Integer teamId;

    @Override
    public String serialize(final Protocol protocol) {
        return "team" + protocol.getKeyValueSeperator() + this.teamId.toString();
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
        return null;
    }

    @Override
    public void setAnimal(final Integer aAnimal) {
        // Has to be empty
    }

    /**
     * Sets the id of the won team.
     * @param id - the id (duh)
     */
    public void setWonTeam(final Integer id) {
        teamId = id;
    }

    /**
     * @return the id of the won team
     */
    public Integer getTeamId() {
        return teamId;
    }
}

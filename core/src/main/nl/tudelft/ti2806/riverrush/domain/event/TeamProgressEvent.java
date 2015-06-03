package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Is fired when a team is a little further on the progressbar.
 */
public class TeamProgressEvent implements Event {

    private int teamID;
    private int progress;

    @Override
    public String serialize(final Protocol protocol) {
        return "teamID" + protocol.getKeyValueSeperator() + teamID + protocol.getPairSeperator()
            + "progress" + protocol.getKeyValueSeperator() + progress;
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        teamID = Integer.parseInt(keyValuePairs.get("teamID"));
        progress = Integer.parseInt(keyValuePairs.get("progress"));
        return this;
    }

    @Override
    public void setAnimal(Integer anPlayerID) {
        //Has to be empty
    }

    @Override
    public Integer getAnimal() {
        // Has to be empty
        return null;
    }

    /**
     * @return the id of the team
     */
    public int getTeamID() {
        return teamID;
    }

    /**
     * @return the progress in an int between 0 and 100
     */
    public int getProgress() {
        return progress;
    }
}

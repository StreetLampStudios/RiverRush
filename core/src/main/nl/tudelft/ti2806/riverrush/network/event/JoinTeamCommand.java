package nl.tudelft.ti2806.riverrush.network.event;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This event is sent from the device to the server.
 */
public class JoinTeamCommand implements Event {

    private Integer animalId;
    private Integer teamId;

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("animal").append(protocol.getKeyValueSeperator()).append(this.animalId.toString());
        stringBuilder.append(protocol.getPairSeperator());
        stringBuilder.append("team").append(protocol.getKeyValueSeperator()).append(this.teamId.toString());
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.containsKey("animal") && keyValuePairs.containsKey("team")) {
            this.animalId = Integer.parseInt(keyValuePairs.get("animal"));
            this.teamId = Integer.parseInt(keyValuePairs.get("team"));
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    public void setAnimal(final AbstractAnimal aAnimal) {
        this.animalId = aAnimal.getId();
    }

    public Integer getAnimal() {
        return this.animalId;
    }

    public void setTeam(final AbstractTeam team) {
        this.teamId = team.getId();
    }

    public Integer getTeam() {
        return this.teamId;
    }
}

package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Domain event for when a player is added to the game.
 */
public class AnimalAddedEvent implements Event {

    private Integer animalId;

    private Integer teamId;

    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een CollidedEvent]";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    @Override
    public void setAnimal(final AbstractAnimal aAnimal) {
        this.animalId = aAnimal.getId();
    }

    @Override
    public Integer getAnimal() {
        return this.animalId;
    }

    public Integer getTeam() {
        return this.teamId;
    }

    public void setTeam(AbstractTeam team) {
        this.teamId = team.getId();
    }
}

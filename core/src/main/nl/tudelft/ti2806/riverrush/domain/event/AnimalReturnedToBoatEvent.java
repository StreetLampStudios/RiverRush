package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * The state where the animal gets back up on the boat after being knocked into the water.
 */
public class AnimalReturnedToBoatEvent implements Event {

    private Integer animalId;

    private Integer teamId;

    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een FallOffEvent]";
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

package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractTeam;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * Domain event for a jumping player.
 */
public class AnimalJumpedEvent implements Event {

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
  public void setAnimal(final Integer anAnimalID) {
    this.animalId = anAnimalID;
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

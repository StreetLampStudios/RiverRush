package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * Domain event for when a player is added to the game.
 */
public class PlayerAddedEvent implements Event {

  private Integer animalID;
  private Integer teamID;

  public Integer getPlayer() {
    return this.animalID;
  }

  public void setAnimalID(final Integer aID) {
    this.animalID = aID;
  }

  @Override
  public String serialize(final Protocol protocol) {
    return "player" + protocol.getKeyValueSeperator() + this.animalID + protocol.getPairSeperator()
        + "team" + protocol.getKeyValueSeperator() + this.teamID;
  }

  @Override
  public Event deserialize(final Map<String, String> keyValuePairs) {
    this.teamID = Integer.parseInt(keyValuePairs.get("team"));
    this.animalID = Integer.parseInt(keyValuePairs.get("player"));
    return this;
  }

  public Integer getTeamID() {
    return this.teamID;
  }
}

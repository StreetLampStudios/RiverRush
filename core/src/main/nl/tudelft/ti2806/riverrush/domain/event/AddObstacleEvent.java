package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * This event is fired when an obstacle needs to be added to the screen.
 */
public class AddObstacleEvent implements Event {

  private double location;
  private Integer teamId;

  @Override
  public String serialize(final Protocol protocol) {
    return "[Serialized string van een ObstacleEvent]";
  }

  @Override
  public Event deserialize(final Map<String, String> keyValuePairs) {
    this.teamId = Integer.parseInt(keyValuePairs.get("team"));
    this.location = Double.parseDouble(keyValuePairs.get("location"));
    return this;
  }

  @Override
  public void setAnimal(final Integer anAnimalID) {
    // Has to be empty
  }

  @Override
  public Integer getAnimal() {
    return 0;
  }

  public double getLocation() {
    return this.location;
  }

  public Integer getTeam() {
    return this.teamId;
  }

  public void setTeam(Integer teamID) {
    this.teamId = teamID;
  }
}

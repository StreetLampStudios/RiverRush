package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * Event raised when the game stops.
 */
public class GameStoppedEvent implements Event {

  @Override
  public String serialize(final Protocol protocol) {
    return "";
  }

  @Override
  public Event deserialize(final Map<String, String> keyValuePairs) {
    return this;
  }

  @Override
  public void setAnimal(final Integer anAnimalID) {
    // Has to be empty
  }

  @Override
  public Integer getAnimal() {
    // Has to be empty
    return null;
  }
}

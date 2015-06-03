package nl.tudelft.ti2806.riverrush.network.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * This event is sent from the device to the server.
 */
public class JumpCommand implements Event {

  private Integer id;

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
    this.id = anAnimalID;
  }

  @Override
  public Integer getAnimal() {
    return this.id;
  }
}

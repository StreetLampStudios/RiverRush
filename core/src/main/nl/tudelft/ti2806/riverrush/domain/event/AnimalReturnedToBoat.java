package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

public class AnimalReturnedToBoat implements Event {

  @Override
  public String serialize(final Protocol protocol) {
    return "[Serialized string van een FallOffEvent]";
  }

  @Override
  public Event deserialize(final Map<String, String> keyValuePairs) {
    return this;
  }

  public void setPlayer(Player player) {

  }

}

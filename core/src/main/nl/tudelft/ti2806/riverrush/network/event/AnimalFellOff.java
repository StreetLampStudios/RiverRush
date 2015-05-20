package nl.tudelft.ti2806.riverrush.network.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

public class AnimalFellOff implements Event {
  private Player player;

  @Override
  public void setPlayer(final Player player) {
    this.player = player;
  }

  @Override
  public Player getPlayer() {
    return this.player;
  }

  @Override
  public String serialize(final Protocol protocol) {
    return "[Serialized string van een FallOffEvent]";
  }

  @Override
  public Event deserialize(final Map<String, String> keyValuePairs) {
    return this;
  }
}

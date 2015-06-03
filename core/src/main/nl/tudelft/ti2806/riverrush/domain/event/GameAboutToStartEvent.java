package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * Event raised when there are enough players in the game.
 */
public class GameAboutToStartEvent implements Event {

  // TODO: Make not hardcoded.
  private static final int FIVE_SECONDS = 5;
  private final int seconds;

  /**
   * Constructs the event with a default waiting time of 5 seconds.
   */
  public GameAboutToStartEvent() {
    this.seconds = FIVE_SECONDS;
  }

  /**
   * Return the number of seconds to wait.
   *
   * @return seconds to wait
   */
  public int getSeconds() {
    return this.seconds;
  }

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

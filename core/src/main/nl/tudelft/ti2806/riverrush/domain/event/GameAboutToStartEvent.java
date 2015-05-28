package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Event raised when there are enough players in the game.
 */
public class GameAboutToStartEvent implements Event {

    private static final int FIVE_SECONDS = 5;
    private final int seconds;

    /**
     * Constructs the event with a default waiting time of 5 seconds.
     */
    public GameAboutToStartEvent() {
        this.seconds = FIVE_SECONDS;
    }

    /**
     * Construct the event.
     *
     * @param timeToWait The amount of seconds to wait before the game starts.
     */
    public GameAboutToStartEvent(final int timeToWait) {
        this.seconds = timeToWait;
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public String serialize(final Protocol protocol) {
        return "";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }
}

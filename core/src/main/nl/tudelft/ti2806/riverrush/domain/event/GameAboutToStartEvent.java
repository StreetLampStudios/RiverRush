package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Event raised when there are enough players in the game.
 */
public class GameAboutToStartEvent implements Event {

    //TODO: Make not hardcoded.
    private static final int FIVE_SECONDS = 5;
    private Integer seconds;

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
        return seconds;
    }

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("seconds").append(protocol.getKeyValueSeperator()).append(this.seconds.toString());
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.containsKey("seconds")) {
            this.seconds = Integer.parseInt(keyValuePairs.get("seconds"));
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    @Override
    public void setAnimal(final Integer aAnimal) {
        // Has to be empty
    }

    @Override
    public Integer getAnimal() {
        // Has to be empty
        return null;
    }
}

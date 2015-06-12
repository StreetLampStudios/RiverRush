package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Event raised when the game is about to wait for players to join.
 * Only used after game finished event
 */
public class GameAboutToWaitEvent extends AbstractAnimalEvent {

    private int timeTillWait;

    @Override
    public String serialize(final Protocol protocol) {
        return super.serialize(protocol) + "time" + protocol.getKeyValueSeperator() + timeTillWait;
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        timeTillWait = Integer.parseInt(keyValuePairs.get("time"));
        return super.deserialize(keyValuePairs);
    }

    public void setTimeTillWait(final int time) {
        this.timeTillWait = time;
    }

    public int getTimeTillWait() {
        return timeTillWait;
    }
}

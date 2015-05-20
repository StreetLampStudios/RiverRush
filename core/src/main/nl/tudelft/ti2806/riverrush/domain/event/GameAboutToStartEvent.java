package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Created by m.olsthoorn on 5/18/2015.
 */
public class GameAboutToStartEvent implements Event {

    private final int seconds;

    public GameAboutToStartEvent() {
        this.seconds = 5;
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
    public void setPlayer(Player player) {

    }
}

package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

public class AssetsLoadedEvent implements Event {
    @Override
    public void setPlayer(Player p) {

    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public String serialize(Protocol protocol) {
        return "";
    }

    @Override
    public Event deserialize(Map<String, String> keyValuePairs) {
        return this;
    }
}

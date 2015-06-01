package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * This is the event that is sent from the server to the device and the renderer to say that the animal
 * has fallen off the boat
 * //TODO calculate everything on the server. Now the renderer sends this event
 */
public class AnimalFellOff implements Event {

    private Player player;

    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een FallOffEvent]";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    @Override
    public void setPlayer(Player aPlayer) {
        this.player = aPlayer;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}

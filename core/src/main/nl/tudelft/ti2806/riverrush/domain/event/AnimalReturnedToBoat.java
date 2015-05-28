package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

/**
 * The state where the animal gets back up on the boat after being knocked into the water.
 */
public class AnimalReturnedToBoat implements Event {

    @Override
    public String serialize(final Protocol protocol) {
        return "[Serialized string van een FallOffEvent]";
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        return this;
    }

    /**
     * Sets the player of this event.
     * @param player - The player
     */
    public void setPlayer(final Player player) {
        // Not yet implemented
    }
}

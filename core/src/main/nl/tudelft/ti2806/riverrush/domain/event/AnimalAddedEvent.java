package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Domain event for when a player is added to the game.
 */
public class AnimalAddedEvent extends AbstractTeamAnimalEvent {

    private Integer variation;

    @Override
    public String serialize(final Protocol protocol) {
        String msg = super.serialize(protocol);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(msg);
        stringBuilder.append(protocol.getPairSeperator());
        stringBuilder.append("variation").append(protocol.getKeyValueSeperator()).append(this.variation.toString());
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        super.deserialize(keyValuePairs);
        if (keyValuePairs.containsKey("variation")) {
            this.variation = Integer.parseInt(keyValuePairs.get("variation"));
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    public void setVariation(final Integer variation) {
        this.variation = variation;
    }

    public Integer getVariation() {
        return this.variation;
    }
}

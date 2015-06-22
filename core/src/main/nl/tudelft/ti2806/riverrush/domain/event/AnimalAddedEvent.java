package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.domain.entity.Sector;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Domain event for when a player is added to the game.
 */
public class AnimalAddedEvent extends AbstractTeamAnimalEvent {

    private Integer variation;
    private Sector sector;

    @Override
    public String serialize(final Protocol protocol) {
        String msg = super.serialize(protocol);
        return msg + protocol.getPairSeperator()
            + "variation" + protocol.getKeyValueSeperator() + this.variation.toString()
            + protocol.getPairSeperator()
            + "sector" + protocol.getKeyValueSeperator() + this.sector;
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        super.deserialize(keyValuePairs);
        if (keyValuePairs.containsKey("variation") && keyValuePairs.containsKey("sector")) {
            this.variation = Integer.parseInt(keyValuePairs.get("variation"));
            this.sector = Sector.valueOf(keyValuePairs.get("sector").toUpperCase());
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    public void setVariation(final Integer newVariation) {
        this.variation = newVariation;
    }

    public Integer getVariation() {
        return this.variation;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(final Sector aSector) {
        this.sector = aSector;
    }
}

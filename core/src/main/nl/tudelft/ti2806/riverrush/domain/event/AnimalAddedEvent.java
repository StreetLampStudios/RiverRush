package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

/**
 * Domain event for when a player is added to the game.
 */
public class AnimalAddedEvent implements Event {

    private Integer animalId;

    private Integer teamId;

    private Integer variation;

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("animal").append(protocol.getKeyValueSeperator()).append(this.animalId.toString());
        stringBuilder.append(protocol.getPairSeperator());
        stringBuilder.append("team").append(protocol.getKeyValueSeperator()).append(this.teamId.toString());
        stringBuilder.append(protocol.getPairSeperator());
        stringBuilder.append("variation").append(protocol.getKeyValueSeperator()).append(this.variation.toString());
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.containsKey("animal") && keyValuePairs.containsKey("team") && keyValuePairs.containsKey("variation")) {
            this.animalId = Integer.parseInt(keyValuePairs.get("animal"));
            this.teamId = Integer.parseInt(keyValuePairs.get("team"));
            this.variation = Integer.parseInt(keyValuePairs.get("variation"));
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    @Override
    public void setAnimal(final Integer aAnimal) {
        this.animalId = aAnimal;
    }

    @Override
    public Integer getAnimal() {
        return this.animalId;
    }

    public void setTeam(final Integer team) {
        this.teamId = team;
    }

    public Integer getTeam() {
        return this.teamId;
    }

    public void setVariation(final Integer variation) {
        this.variation = variation;
    }

    public Integer getVariation() {
        return this.variation;
    }
}

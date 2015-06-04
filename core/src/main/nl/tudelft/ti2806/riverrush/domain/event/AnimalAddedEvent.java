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

    private String color;

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("animal").append(protocol.getKeyValueSeperator()).append(this.animalId.toString());
        stringBuilder.append(protocol.getPairSeperator());
        stringBuilder.append("team").append(protocol.getKeyValueSeperator()).append(this.teamId.toString());
        stringBuilder.append(protocol.getPairSeperator());
        stringBuilder.append("color").append(protocol.getKeyValueSeperator()).append(this.color);
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.containsKey("animal") && keyValuePairs.containsKey("team") && keyValuePairs.containsKey("color")) {
            this.animalId = Integer.parseInt(keyValuePairs.get("animal"));
            this.teamId = Integer.parseInt(keyValuePairs.get("team"));
            this.color = keyValuePairs.get("color");
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    @Override
    public Integer getAnimal() {
        return this.animalId;
    }

    @Override
    public void setAnimal(final Integer aAnimal) {
        this.animalId = aAnimal;
    }

    public Integer getTeam() {
        return this.teamId;
    }

    public String getColor() {
        return this.color;
    }

    public void setTeam(final Integer team) {
        this.teamId = team;
    }

    public void setColor(final String color) {
        this.color = color;
    }
}

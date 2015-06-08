package nl.tudelft.ti2806.riverrush.domain.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

public class BoatCollidedEvent implements Event {

    private Integer animalId;

    private Integer teamId;

    private Direction direction;

    @Override
    public String serialize(final Protocol protocol) {
        return "team" + protocol.getKeyValueSeperator() + this.teamId.toString()
                + protocol.getPairSeperator() + "direction" + this.direction.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.containsKey("team") && keyValuePairs.containsKey("direction")) {
            this.teamId = Integer.parseInt(keyValuePairs.get("team"));
            this.direction = Direction.valueOf(keyValuePairs.get("direction").toUpperCase());
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

    public void setTeam(final Integer team) {
        this.teamId = team;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }
}

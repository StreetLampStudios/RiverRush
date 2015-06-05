package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

public class AnimalMovedEvent extends AnimalJumpedEvent {
    private Integer animalId;

    private Integer teamId;



    public enum Direction {
        LEFT, RIGHT, NEUTRAL;
    }
    private Direction direction;

    @Override
    public String serialize(final Protocol protocol) {
        return super.serialize(protocol) + protocol.getPairSeperator()
            + "directionCode" + protocol.getKeyValueSeperator() + direction;
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        super.deserialize(keyValuePairs);

        if (keyValuePairs.containsKey("direction")) {
            direction = Direction.valueOf(keyValuePairs.get("direction").toUpperCase());
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
        return direction;
    }

    public void setDirection(final Direction dir) {
        this.direction = dir;
    }
}

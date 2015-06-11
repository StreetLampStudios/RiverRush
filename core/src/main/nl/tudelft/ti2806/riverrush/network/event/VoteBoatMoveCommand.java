package nl.tudelft.ti2806.riverrush.network.event;

import java.util.Map;

import nl.tudelft.ti2806.riverrush.domain.event.AbstractAnimalEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalMovedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

public class VoteBoatMoveCommand implements Event {

    private Direction direction;
    private Integer animal;

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(protocol.getPairSeperator());
        stringBuilder.append("direction").append(protocol.getKeyValueSeperator())
                .append(this.direction.toString());
        return stringBuilder.toString();
    }

    @Override
    public Event deserialize(final Map<String, String> keyValuePairs) {
        if (keyValuePairs.containsKey("direction")) {
            this.direction = Direction.valueOf(keyValuePairs.get("direction").toUpperCase());
        } else {
            throw new InvalidProtocolException("Does not contain all the keys");
        }
        return this;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(final Direction dir) {
        this.direction = dir;
    }

    @Override
    public void setAnimal(final Integer animalID) {
        this.animal = animalID;
    }

    @Override
    public Integer getAnimal() {
        return this.animal;
    }
}

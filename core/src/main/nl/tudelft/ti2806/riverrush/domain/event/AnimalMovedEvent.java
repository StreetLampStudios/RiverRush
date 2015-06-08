package nl.tudelft.ti2806.riverrush.domain.event;

import nl.tudelft.ti2806.riverrush.network.protocol.InvalidProtocolException;
import nl.tudelft.ti2806.riverrush.network.protocol.Protocol;

import java.util.Map;

public class AnimalMovedEvent extends AbstractTeamAnimalEvent {

    private Direction direction;

    @Override
    public String serialize(final Protocol protocol) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.serialize(protocol));
        stringBuilder.append(protocol.getPairSeperator());
        stringBuilder.append("direction").append(protocol.getKeyValueSeperator()).append(this.direction.toString());
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
}

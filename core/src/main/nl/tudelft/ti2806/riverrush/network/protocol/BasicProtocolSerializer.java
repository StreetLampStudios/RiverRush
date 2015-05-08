package nl.tudelft.ti2806.riverrush.network.protocol;

import nl.tudelft.ti2806.riverrush.failfast.FailIf;

import java.util.Map;
import java.util.TreeMap;

/**
 * Basic implementation of the protocol serializer.
 */
public class BasicProtocolSerializer implements ProtocolSerializer {

    /**
     * Serialize a network message to a string
     *
     * @param message message to be send
     * @return String
     */
    @Override
    public String serialize(NetworkMessage message) {
        FailIf.isNull(message);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("action=").append(message.getAction());

        for (String key : message.getKeys()) {
            try {
                stringBuilder.append(";").append(key).append("=").append(message.getValue(key));
            } catch (NonExistingKeyException e) {
                // Never happens
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Deserialize a network message to a NetworkMessage
     *
     * @param message message send over network
     * @return NetworkMessage
     */
    @Override
    public NetworkMessage deserialize(String message) throws InvalidProtocolException, InvalidActionException {
        FailIf.isNull(message);

        Map<String, String> map = new TreeMap<>();
        String action = "";

        String[] pairs = message.toLowerCase().split(";");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length != 2) {
                throw new InvalidProtocolException();
            }

            if (keyValue[0].equals("action")) {
                action = keyValue[1];
            } else {
                map.put(keyValue[0], keyValue[1]);
            }
        }

        return new NetworkMessage(action, map);
    }
}

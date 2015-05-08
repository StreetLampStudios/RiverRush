package nl.tudelft.ti2806.riverrush.network.protocol;

/**
 * Interface for network protocol serializers.
 */
public interface ProtocolSerializer {

    /**
     * Serialize a network message to a string
     *
     * @param message message to be send
     * @return String
     */
    String serialize(final NetworkMessage message);

    /**
     * Deserialize a network message to a NetworkMessage
     *
     * @param message message send over network
     * @return NetworkMessage
     */
    NetworkMessage deserialize(final String message) throws InvalidProtocolException, InvalidActionException;
}

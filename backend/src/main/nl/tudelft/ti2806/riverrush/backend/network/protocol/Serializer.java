package nl.tudelft.ti2806.riverrush.backend.network.protocol;

/**
 * Created by thomas on 7-5-15.
 */
public interface Serializer {
    String serialize(final NetworkMessage message);
    NetworkMessage deserialize(final String message);
}

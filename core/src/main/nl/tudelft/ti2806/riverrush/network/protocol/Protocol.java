package nl.tudelft.ti2806.riverrush.network.protocol;

import nl.tudelft.ti2806.riverrush.network.event.NetworkEvent;

public interface Protocol {
    void registerNetworkAction(Class<? extends NetworkEvent> eventClass, EventInstantiator eventInstatiator);
    boolean isRegistered(Class<? extends NetworkEvent> eventClass);

    NetworkEvent deserialize(String event) throws InvalidProtocolException, InvalidActionException;
    String serialize(NetworkEvent event);

    int getPortNumber();

    @FunctionalInterface
    interface EventInstantiator {
        NetworkEvent instantiate();
    }
}

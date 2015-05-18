package nl.tudelft.ti2806.riverrush.domain.event.listener;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.network.event.SendEvent;

/**
 * Created by thomas on 18-5-15.
 */
public class SendEventListener extends EventListener<SendEvent> {

    private Sender sender;

    public SendEventListener() {
    }

    @Override
    public void handle(SendEvent event, EventDispatcher dispatcher) {
        sender.send(event, dispatcher);
    }

    @Override
    public Class<SendEvent> getEventType() {
        return SendEvent.class;
    }

    public void onHandle(Sender sender) {
        this.sender = sender;
    }

    @FunctionalInterface
    public interface Sender {
        void send(SendEvent event, EventDispatcher dispatcher);
    }
}

package nl.tudelft.ti2806.riverrush.domain.event;

@FunctionalInterface
public interface HandlerLambda<T extends Event> {
    void handle(T event);
}

package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.Monkey;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

public class AnimalInWater implements AnimalState {

    private Monkey monkey;
    private final EventDispatcher dispatcher;

    public AnimalInWater(Monkey monk, EventDispatcher eventDispatcher) {
        this.monkey = monk;
        this.dispatcher = eventDispatcher;
    }

    @Override
    public AnimalState jump() {
        return this;
    }

    @Override
    public AnimalState drop() {
        return this;
    }

    @Override
    public AnimalState collide() {
        return this;
    }

}

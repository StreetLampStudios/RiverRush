package nl.tudelft.ti2806.riverrush.domain.entity.state;

import com.badlogic.gdx.scenes.scene2d.Action;
import nl.tudelft.ti2806.riverrush.domain.entity.Monkey;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalFellOff;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * This is the standard state.
 */
public class AnimalOnBoat implements AnimalState {

    private Monkey monkey;
    private final EventDispatcher dispatcher;

    /**
     * Constructor.
     * @param monk
     * @param eventDispatcher
     */
    public AnimalOnBoat(final Monkey monk, final EventDispatcher eventDispatcher) {
        this.monkey = monk;
        this.dispatcher = eventDispatcher;
    }

    @Override
    public AnimalState jump() {
        return new AnimalInAir(this.monkey, this.dispatcher);
    }

    @Override
    public AnimalState drop() {
        return this;
    }

    @Override
    public AnimalState collide() {
        Action hit = this.monkey.getHit();
        this.monkey.addAction(hit);
        this.dispatcher.dispatch(new AnimalFellOff());
        return new AnimalInWater(this.monkey, this.dispatcher);
    }
}

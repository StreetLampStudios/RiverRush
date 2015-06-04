package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalDroppedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

import java.util.Timer;
import java.util.TimerTask;

/**
 * State in which the animal is in mid-air. This means the player can't control the animal while in
 * this state.
 */
public class AnimalInAir extends AbstractAnimalState {

    private static final int DROP_DELAY = 5000;
    private Animal animal;
    private final Timer tmr;

    /**
     * Constructor.
     *
     * @param eventDispatcher - The event dispatcher of this event
     */
    public AnimalInAir(final Animal anim, final EventDispatcher eventDispatcher) {
        super(eventDispatcher);
        this.animal = anim;
        tmr = new Timer();
        tmr.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                AnimalInAir.this.animal.drop();
                AnimalInAir.this.tmr.cancel();
            }
        }, DROP_DELAY, DROP_DELAY);
    }

    @Override
    public AnimalState jump() {
        return this;
    }

    @Override
    public AnimalState drop() {
        AnimalDroppedEvent event = new AnimalDroppedEvent();
        event.setAnimal(this.animal.getId());
        event.setTeam(this.animal.getTeamId());
        this.getDispatcher().dispatch(event);
        return new AnimalOnBoat(this.animal, this.getDispatcher());
    }

    @Override
    public AnimalState collide() {
        return this;
    }

    @Override
    public AnimalState returnToBoat() {
        return this;
    }
}

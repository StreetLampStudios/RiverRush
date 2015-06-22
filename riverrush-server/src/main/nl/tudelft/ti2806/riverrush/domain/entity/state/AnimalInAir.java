package nl.tudelft.ti2806.riverrush.domain.entity.state;

import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalDroppedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

import java.util.Timer;
import java.util.TimerTask;

/**
 * State in which the animal is in mid-air. This means the player can't control the animal while in
 * this state.
 */
public class AnimalInAir extends AbstractAnimalState {

    private static final int DROP_DELAY = 1000
    private final Animal animal;
    private final Timer tmr;

    /**
     * Constructor.
     *
     * @param eventDispatcher - The event dispatcher of this event
     * @param anim            - The animal to which the state belongs
     */
    public AnimalInAir(final Animal anim, final EventDispatcher eventDispatcher) {
        super(eventDispatcher);
        this.animal = anim;
        this.tmr = new Timer();
        this.tmr.scheduleAtFixedRate(new TimerTask() {
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
    public AnimalState fall() {
        return this;
    }

    @Override
    public AnimalState returnToBoat() {
        return this;
    }

    @Override
    public AnimalState voteDirection(final Direction direction) {
        return this;
    }

    @Override
    public boolean isOnBoat() {
        return true;
    }
}

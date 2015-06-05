package nl.tudelft.ti2806.riverrush.domain.entity.state;

import java.util.Timer;
import java.util.TimerTask;

import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.AnimalReturnedToBoatEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * This state means that the animal is not on the boat.
 */
public class AnimalInWater extends AbstractAnimalState {

    private Animal animal;

    private static final int RESPAWN_DELAY = 2000;

    /**
     * Constructor.
     *
     * @param anim - the animal of the stat
     * @param eventDispatcher - The event disptacher
     */
    public AnimalInWater(final Animal anim, final EventDispatcher eventDispatcher) {
        super(eventDispatcher);
        this.animal = anim;
        Timer tmr = new Timer();
        tmr.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                animal.returnToBoat();
                tmr.cancel();
            }
        }, RESPAWN_DELAY, RESPAWN_DELAY);
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

    @Override
    public AnimalState returnToBoat() {
        AnimalReturnedToBoatEvent event = new AnimalReturnedToBoatEvent();
        event.setAnimal(this.animal.getId());
        event.setTeam(this.animal.getTeamId());
        this.getDispatcher().dispatch(event);

        return new AnimalOnBoat(this.animal, this.getDispatcher());
    }

}
package nl.tudelft.ti2806.riverrush.graphics.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Sector;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.state.AnimalOnBoat;

/**
 * Graphics animal.
 */
public class Animal extends AbstractAnimal {

    /**
     * Create an animal.
     *
     * @param dispatcher The event dispatcher
     * @param id         The id of the animal
     * @param team       The team the animal belongs in
     * @param variation  The variation of the animal
     * @param boatSector The sector the animal should be in
     */
    public Animal(final EventDispatcher dispatcher,
                  final Integer id,
                  final Integer team,
                  final Integer variation,
                  final Sector boatSector) {
        super(dispatcher, id);
        this.setTeamId(team);
        this.setVariation(variation);
        this.setSectorOnBoat(boatSector);
    }

    private AnimalActor actor;

    /**
     * @return this animal's graphical component: MonkeyActor.
     */
    public AnimalActor getActor() {
        return this.actor;
    }

    /**
     * Set the actor of the animal.
     *
     * @param aActor The actor to set
     */
    public void setActor(final AnimalActor aActor) {
        this.actor = aActor;
        this.setState(new AnimalOnBoat(this, this.getDispatcher()));
    }

    /**
     * Determine what happens when an animal in this state collides.
     */
    public void collide() {
        this.getState().collide();
    }
}

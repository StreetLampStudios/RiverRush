package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.scenes.scene2d.Action;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Sector;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.state.AnimalOnBoat;

/**
 * The basic animal.
 */
public class Animal extends AbstractAnimal {

    private AnimalActor actor;

    /**
     * Constructor.
     *
     * @param dispatch   - The EventDispatcher
     * @param id         - ID of the animal
     * @param team       - TeamID of the animal
     * @param variation  - Image Variation of the animal
     * @param boatSector - In which sector the animal should be placed
     */
    public Animal(final EventDispatcher dispatch,
                  final Integer id,
                  final Integer team,
                  final Integer variation,
                  final Sector boatSector) {
        super(dispatch, id);
        this.setTeamId(team);
        this.setVariation(variation);
        this.setSectorOnBoat(boatSector);
    }

    /**
     * Sets the actor.
     *
     * @param act - The animalactor
     */
    public void setActor(final AnimalActor act) {
        this.actor = act;
        this.setState(new AnimalOnBoat(this, this.getDispatcher()));
    }

    /**
     * Make the actor create a fadeAction.
     *
     * @return the action to be used in the state.
     */
    public Action returnFade() {
        return this.actor.returnFade();
    }

    /**
     * Make the actor create a returnAction.
     *
     * @return the action to be used in the state.
     */
    public Action returnMove() {
        return this.actor.returnMove();
    }

    /**
     * Make the actor create a jumpAction.
     *
     * @return the action to be used in the state.
     */
    public Action jumpAction() {
        return this.actor.jumpAction();
    }

    /**
     * Make the actor create a collideAction.
     *
     * @return the action to be used in the state.
     */
    public Action collideAction() {
        return this.actor.collideAction();
    }

    /**
     * @return this animal's graphical component: MonkeyActor.
     */
    public AnimalActor getActor() {
        return this.actor;
    }

}

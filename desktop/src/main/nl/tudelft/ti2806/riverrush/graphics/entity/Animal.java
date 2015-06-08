package nl.tudelft.ti2806.riverrush.graphics.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.state.AnimalOnBoat;

import com.badlogic.gdx.scenes.scene2d.Action;

public class Animal extends AbstractAnimal {

    public Animal(EventDispatcher dispatch, Integer id, Integer team) {
        super(dispatch, id);
        this.setTeamId(team);
    }

    private AnimalActor actor;

    public void setActor(AnimalActor act) {
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

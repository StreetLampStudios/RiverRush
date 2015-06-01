package nl.tudelft.ti2806.riverrush.graphics.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.AnimalState;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

import com.badlogic.gdx.scenes.scene2d.Action;

public class Animal extends AbstractAnimal {

    public Animal(EventDispatcher dispatcher, AnimalState state) {
        super(dispatcher, state);
    }

    private MonkeyActor actor;

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
     * @return this animal's graphical component: Monkeyactor.
     */
    public MonkeyActor getActor() {
        return this.actor;
    }

}

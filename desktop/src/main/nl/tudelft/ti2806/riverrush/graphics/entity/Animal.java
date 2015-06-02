package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.scenes.scene2d.Action;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.state.AnimalState;

public class Animal extends AbstractAnimal {

    public Animal(AnimalState state) {
        super(state);
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

package nl.tudelft.ti2806.riverrush.graphics.entity;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.state.AnimalOnBoat;

import com.badlogic.gdx.scenes.scene2d.Action;

public class Animal extends AbstractAnimal {

    private MonkeyActor actor;

    public Animal(final EventDispatcher dispatcher) {
        this.setState(new AnimalOnBoat(this, dispatcher));
    }

    public Action returnFade() {
        return this.actor.returnFade();
    }

    public Action returnMove() {
        return this.actor.returnMove();
    }

    public Action jumpAction() {
        return this.actor.jumpAction();
    }

    public Action collideAction() {
        return this.actor.collideAction();
    }

}

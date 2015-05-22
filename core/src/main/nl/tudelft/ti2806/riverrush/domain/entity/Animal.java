package nl.tudelft.ti2806.riverrush.domain.entity;

import com.badlogic.gdx.scenes.scene2d.Action;

/**
 * All common actions that an animal can perform.
 */
public interface Animal {
    /**
     * Called when the player jumps.
     * @return the action that should be performed
     */
    Action jump();

    /**
     * Called when the animal disconnects.
     */
    void leave();
}

package nl.tudelft.ti2806.monkeyrush.domain.entity;

/**
 * Created by m.olsthoorn on 5/8/2015.
 */
public interface Animal {
    /**
     * Called when the player jumps.
     */
    void jump();

    /**
     * Called when the animal disconnects.
     */
    void leave();
}

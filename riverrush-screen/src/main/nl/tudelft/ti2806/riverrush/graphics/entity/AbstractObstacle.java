package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The base class for obstacles.
 */
public class AbstractObstacle extends Actor {
    private Circle bounds;

    public Circle getBounds() {
        return this.bounds;
    }

    public void setBounds(final Circle newBounds) {
        this.bounds = newBounds;
    }
}

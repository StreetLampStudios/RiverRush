package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class AbstractObstacle extends Actor {

    /**
     * Calculates whether or not this obtacle is currently colliding with the given monkey. We find
     * a collision to be true if any part of the monkey is within the bounds of the obstacle.
     *
     * @param monk refers to the monkey for which the collision has to be calculated
     * @return true if collision occurs, false if it doesn't.
     */
    public boolean calculateCollision(final MonkeyActor monk) {
        float monkx = monk.getX();
        float monkxedge = monk.getX() + monk.getWidth();
        float monky = monk.getY();
        float monkyedge = monk.getY() + monk.getHeight();
        float[] x = {monkx, monkxedge};
        float[] y = {monky, monkyedge};

        for (float edgex : x) {
            for (float edgey : y) {
                if (edgex < this.getX() + this.getWidth() && edgex > this.getX()
                        && edgey < this.getY() + this.getHeight() && edgey > this.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

}

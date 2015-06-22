package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nl.tudelft.ti2806.riverrush.graphics.entity.BoatGroup;
import nl.tudelft.ti2806.riverrush.graphics.entity.CannonBallGraphic;
import nl.tudelft.ti2806.riverrush.graphics.entity.RiverActor;
import nl.tudelft.ti2806.riverrush.graphics.entity.RockGraphic;

/**
 * This class defined the side stage. The side stage always holds a river and a boat as well as
 * anything that occurs within those.
 */
public class SideStage extends Stage {

    private final RiverActor river;

    private BoatGroup boat;

    /**
     * Creates a stage that holds the river, boats, and any player characters that reside on it, as
     * well as the obstacles that pass through it.
     */
    public SideStage() {
        this.river = new RiverActor(0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.getRoot().addActorAt(0, this.river);
    }

    /**
     * Adds a new obstacle to the screen.
     *
     * @param graphic - The obstacle that you want to add.
     */
    public void spawnObstacle(final CannonBallGraphic graphic) {
        graphic.init();
        this.boat.addActorAt(0, graphic);
    }

    /**
     * Adds a new obstacle to the screen.
     *
     * @param graphic - The obstacle that you want to add.
     */
    public void spawnRock(final RockGraphic graphic) {
        graphic.init();
        this.getRoot().addActorBefore(this.boat, graphic);
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    /**
     * @return the river that belongs to this stage.
     */
    public RiverActor getRiver() {
        return this.river;
    }

    /**
     * Resize the stage based on the resolution of the screen.
     *
     * @param width  the new base width.
     * @param height the new base height.
     */
    public void resize(final int width, final int height) {
        if (this.boat != null) {
            this.boat.resize(width, height);
        }
    }

    /**
     * Set the boat corresponding to the given stage.
     *
     * @param newBoat refers to the boat that needs to be added to this stage.
     */
    public void setBoat(final BoatGroup newBoat) {
        this.boat = newBoat;
        this.getRoot().addActorAt(1, newBoat);
    }
}

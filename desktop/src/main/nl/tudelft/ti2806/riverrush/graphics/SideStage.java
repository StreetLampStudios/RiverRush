package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.scenes.scene2d.Stage;
import nl.tudelft.ti2806.riverrush.graphics.entity.CannonBallGraphic;
import nl.tudelft.ti2806.riverrush.graphics.entity.RiverActor;
import nl.tudelft.ti2806.riverrush.graphics.entity.RockGraphic;

/**
 * This class defined the side stage. The side stage always holds a river and a boat as well as
 * anything that occurs within those.
 */
public class SideStage extends Stage {

    private final RiverActor river;
    private CannonBallGraphic obstacle;
    private RockGraphic rock;

    private static final int RIVER_WIDTH = 1920;
    private static final int RIVER_HEIGHT = 1080;

    /**
     * Creates a stage that holds the river, boats, and any player characters that reside on it, as
     * well as the obstacles that pass through it.
     */
    public SideStage() {
        this.river = new RiverActor(0, RIVER_WIDTH, RIVER_HEIGHT);
        this.addActor(this.river);
    }

    /**
     * Adds a new obstacle to the screen.
     *
     * @param graphic - The obstacle that you want to add.
     */
    public void spawnObstacle(final CannonBallGraphic graphic) {
        if (this.obstacle != null) {
            this.obstacle.remove();
        }
        graphic.init();
        this.obstacle = graphic;
        this.addActor(this.obstacle);
    }

    /**
     * Adds a new obstacle to the screen.
     *
     * @param graphic - The obstacle that you want to add.
     */
    public void spawnRock(final RockGraphic graphic) {
        if (this.rock != null) {
            this.rock.remove();
        }
        graphic.init();
        this.rock = graphic;
        this.addActor(this.rock);
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
}

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
    private CannonBallGraphic obstacle;
    private RockGraphic rock;

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
        this.obstacle = graphic;
        this.boat.addActorAt(0, this.obstacle);
    }

    /**
     * Adds a new obstacle to the screen.
     *
     * @param graphic - The obstacle that you want to add.
     */
    public void spawnRock(final RockGraphic graphic) {
        graphic.init();
        this.rock = graphic;
        this.getRoot().addActorBefore(this.boat, this.rock);
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

    public void resize(int width, int height) {
        if (this.boat != null) {
            this.boat.resize(width, height);
        }
    }

    public void setBoat(BoatGroup newBoat) {
        this.boat = newBoat;
        this.getRoot().addActorAt(1, newBoat);
    }
}

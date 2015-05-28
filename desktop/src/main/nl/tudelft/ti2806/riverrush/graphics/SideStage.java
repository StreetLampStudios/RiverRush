package nl.tudelft.ti2806.riverrush.graphics;

import nl.tudelft.ti2806.riverrush.domain.entity.Boat;
import nl.tudelft.ti2806.riverrush.domain.entity.Lose;
import nl.tudelft.ti2806.riverrush.domain.entity.Monkey;
import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.entity.River;
import nl.tudelft.ti2806.riverrush.domain.entity.Win;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.inject.Inject;

/**
 * This class defined the side stage. The side stage always holds a river and a boat as well as
 * anything that occurs within those.
 *
 */
public class SideStage extends Table {

    private final Boat boat;
    private final River river;
    private ObstacleGraphic obstacle;
    private final AssetManager assets;

    private static final int RIVER_HEIGHT = 1920;
    private static final int RIVER_WIDTH = 1080;
    private static final int BOAT_OFFSET = 300;
    private static final int BOAT_POSITION = 150;
    private static final int BOAT_SIZE = 600;
    private static final double OBSTACLE_OFFSET = 0.5;

    /**
     * Creates a stage that holds the river, boats, and any player characters that reside on it, as
     * well as the obstacles that pass through it.
     *
     * @param assetManager
     *            refers to the manager that has made all loaded assets available for use.
     * @param width
     *            is the width size that the stage will be given.
     * @param height
     *            is the height size the stage will be given.
     * @param eventDispatcher
     *            is the dispatcher that handles all relevant events.
     */
    @Inject
    public SideStage(final AssetManager assetManager, final float width, final float height,
            final EventDispatcher eventDispatcher) {
        this.setBounds(0, 0, width, height);
        this.assets = assetManager;
        this.river = new River(this.assets, 0, RIVER_HEIGHT, RIVER_WIDTH);
        this.boat = new Boat(this.assets, this.river.getMid() - BOAT_OFFSET, BOAT_POSITION,
                BOAT_SIZE, BOAT_SIZE, eventDispatcher);
        this.addActor(this.river);
        this.addActor(this.boat);

        this.spawnObstacle(OBSTACLE_OFFSET);

    }

    /**
     * Adds a new obstacle to the screen.
     *
     * @param offset
     *            - double !! between 0 and 1 !!
     */
    public void spawnObstacle(final double offset) {
        if (this.obstacle != null) {
            this.removeActor(this.obstacle);
        }
        this.obstacle = new ObstacleGraphic(this.assets, offset);
        this.addActor(this.obstacle);
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        super.draw(batch, parentAlpha);
        // We check if the current obstacle hits the only currently available
        // player (the player parameter is currently unusued)
        Monkey monk = this.boat.getAnimal(new Player());
        if (this.obstacle != null && this.obstacle.collide(monk)) {
            monk.collide();
        }

        if (this.obstacle != null && this.obstacle.isDone()) {
            this.spawnObstacle(OBSTACLE_OFFSET);
        }

    }

    /**
     * Is called when the team on this stage has won.
     */
    public void win() {
        this.addActor(new Win(this.assets));
        this.boat.addAction(Actions.fadeOut(1f));
    }

    /**
     * Is called when the team on this stage has lost.
     */
    public void lose() {
        this.addActor(new Lose(this.assets));
        this.boat.addAction(Actions.fadeOut(1f));
    }

    /**
     * @return the boat that is on this stage.
     */
    public Boat getBoat() {
        return this.boat;
    }

    /**
     * @return the river that belongs to this stage.
     */
    public River getRiver() {
        return this.river;
    }
}

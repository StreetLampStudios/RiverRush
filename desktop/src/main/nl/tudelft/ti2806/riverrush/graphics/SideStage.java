package nl.tudelft.ti2806.riverrush.graphics;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.entity.BoatGroup;
import nl.tudelft.ti2806.riverrush.graphics.entity.ObstacleGraphic;
import nl.tudelft.ti2806.riverrush.graphics.entity.RiverActor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.inject.Inject;

/**
 * This class defined the side stage. The side stage always holds a river and a boat as well as
 * anything that occurs within those.
 */
public class SideStage extends Table {

    private BoatGroup boat;
    private final RiverActor river;
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
     *            - the event dispatcher
     */
    @Inject
    public SideStage(final AssetManager assetManager, final float width, final float height,
            final EventDispatcher eventDispatcher) {
        this.setBounds(0, 0, width, height);
        this.assets = assetManager;
        this.river = new RiverActor(this.assets, 0, RIVER_HEIGHT, RIVER_WIDTH);
        this.addActor(this.river);
    }

    /**
     * Adds a new obstacle to the screen.
     *
     * @param graphic
     *            - The obstacle that you want to add.
     */
    public void spawnObstacle(final ObstacleGraphic graphic) {
        if (this.obstacle != null) {
            this.removeActor(this.obstacle);
        }
        graphic.init();
        this.obstacle = graphic;
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
        // MonkeyActor monk = this.boat.getAnimal(new Player());
        // if (this.obstacle != null && this.obstacle.collide(monk)) {
        // monk.collide();
        // }
        //
        // if (this.obstacle != null && this.obstacle.isDone()) {
        // this.spawnObstacle(OBSTACLE_OFFSET);
        // }
    }

    // /**
    // * Is called when the team on this stage has won.
    // */
    // public void win() {
    // this.addActor(new WinActor(this.assets));
    // this.boat.addAction(Actions.fadeOut(1f));
    // }
    //
    // /**
    // * Is called when the team on this stage has lost.
    // */
    // public void lose() {
    // this.addActor(new LoseActor(this.assets));
    // this.boat.addAction(Actions.fadeOut(1f));
    // }

    /**
     * @return the boat that is on this stage.
     */
    public BoatGroup getBoat() {
        return this.boat;
    }

    /**
     * @return the river that belongs to this stage.
     */
    public RiverActor getRiver() {
        return this.river;
    }
}

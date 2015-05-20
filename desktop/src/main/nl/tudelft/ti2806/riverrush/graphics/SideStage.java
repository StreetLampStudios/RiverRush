package nl.tudelft.ti2806.riverrush.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.domain.entity.*;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

public class SideStage extends Table {

    private Boat boat;
    private River river;
    private ObstacleGraphic obstacle;
    private AssetManager assets;

    /**
     * Creates a stage for the river, etc.
     *
     * @param assets2
     * @param width   of the bank
     * @param height
     */
    @Inject
    public SideStage(final AssetManager assets2, final float width,
                     final float height, EventDispatcher eventDispatcher) {
        this.setBounds(0, 0, width, height);
        this.assets = assets2;
        this.river = new River(this.assets, 0, 1920, 1080);
        this.boat = new Boat(this.assets, this.river.getMid() - 300, 150, 600,
            600, eventDispatcher);
        this.addActor(this.river);
        this.addActor(this.boat);

        this.spawnObstacle(0.5);

    }

    /**
     * Adds a new obstacle to the screen.
     *
     * @param offset - double !! between 0 and 1 !!
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
            this.spawnObstacle(0.5);
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

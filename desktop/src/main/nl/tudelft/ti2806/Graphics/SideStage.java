package nl.tudelft.ti2806.Graphics;

import nl.tudelft.ti2806.riverrush.domain.entity.Boat;
import nl.tudelft.ti2806.riverrush.domain.entity.Lose;
import nl.tudelft.ti2806.riverrush.domain.entity.River;
import nl.tudelft.ti2806.riverrush.domain.entity.Win;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.google.inject.Inject;

public class SideStage extends AbstractStage {

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
    public SideStage(final AssetManager assets2, final float width, final float height) {
        this.setBounds(0, 0, width, height);
        this.assets = assets2;
        this.river = new River(assets, 0, 1920, 1080);
        this.boat = new Boat(assets, this.river.getMid() - 300, 150, 600, 600);

        this.addActor(this.river);
        this.addActor(this.boat);

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
    }

    /**
     * Is called when the team on this stage has won.
     */
    public void win() {
        this.addActor(new Win(assets));
        this.boat.addAction(Actions.fadeOut(1f));
    }

    /**
     * Is called when the team on this stage has lost.
     */
    public void lose() {
        this.addActor(new Lose(assets));
        this.boat.addAction(Actions.fadeOut(1f));
    }
}

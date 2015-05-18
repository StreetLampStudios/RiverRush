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

    @Inject
    public SideStage(AssetManager assets, float width, float height) {
        this.setBounds(0, 0, width, height);
        this.assets = assets;
        this.river = new River(assets, 0, 1920, 1080);
        this.boat = new Boat(assets, this.river.getMid() - 300, 150, 600, 600);

        this.addActor(this.river);
        this.addActor(this.boat);

    }

    /**
     * Adds a new obstacle to the screen
     *
     * @param offset
     *            - double !! between 0 and 1 !!
     */
    public void spawnObstacle(double offset) {
        if (this.obstacle != null) {
            this.removeActor(this.obstacle);
        }
        this.obstacle = new ObstacleGraphic(this.assets, offset);
        this.addActor(this.obstacle);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // TODO: Temporary code to add new cannonball
        if (this.obstacle != null && this.obstacle.isDone()) {
            this.spawnObstacle(1.0);
        }
    }

    public void win(AssetManager assets) {
        this.addActor(new Win(assets));
        this.boat.addAction(Actions.fadeOut(1f));
    }

    public void lose(AssetManager assets) {
        this.addActor(new Lose(assets));
        this.boat.addAction(Actions.fadeOut(1f));
    }
}

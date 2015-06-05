package nl.tudelft.ti2806.riverrush.graphics.entity;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.google.inject.Inject;

/**
 * Represents a boat that the animals row on.
 */
public class BoatGroup extends Group {

    private final int SIZE = 900;

    /**
     * The asset manager.
     */
    private final AssetManager manager;
    /**
     * Specifies dimension x.
     */
    private static final int REGION_ENDX = 584;
    /**
     * Specifies dimension y.
     */
    private static final int REGION_ENDY = 1574;

    private final ArrayList<BoatSector> sectors;

    private Iterator<BoatSector> iterator;

    private static final float MOVE_DISTANCE = 200;

    private static final int NUM_SECTORS = 5;
    private static final int COL_COUNT = 5;
    private static final int ROW_COUNT = 2;

    private final Texture tex;

    /**
     * Creates an boat object with a given graphical representation.
     *
     * @param assetManager enables the object to retrieve its assets
     * @param xpos represents the position of the boat on the x axis
     * @param ypos represents the position of the boat on the y axis
     */
    @Inject
    public BoatGroup(final AssetManager assetManager, final float xpos, final float ypos) {
        this.manager = assetManager;
        this.setX(xpos);
        this.setY(ypos);
        this.setWidth(this.SIZE);
        this.setHeight(this.SIZE);

        this.tex = this.manager.get("data/ship.png", Texture.class);
        // this.region = new TextureRegion(tex, 0, 0, tex.getWidth(), tex.getHeight());

        this.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        this.setOriginX((this.getWidth() / 2));
        this.setOriginY((this.getHeight() / 2));

        this.sectors = new ArrayList<>();
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.WHITE);

        for (int i = NUM_SECTORS - 1; i >= 0; i--) {
            Color color = colors.get(i);
            BoatSector sec = new BoatSector(assetManager, ROW_COUNT, COL_COUNT, color);
            float secPosX = (this.getWidth() / 2) - (sec.getWidth() / 2);
            float secPosY = 50f + ((20f + sec.getHeight()) * i);
            sec.setPosition(secPosX, secPosY);
            this.sectors.add(sec);
            this.addActor(sec);
        }
        this.iterator = this.sectors.iterator();
        this.iterator.next(); // Start with the second sector

    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        // batch.draw(this.region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
        // this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
        // this.getRotation());
        batch.draw(this.tex, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
                this.getRotation(), 0, 0, this.tex.getWidth(), this.tex.getHeight(), false, false);

        batch.setColor(Color.WHITE);

        super.draw(batch, parentAlpha);
        batch.disableBlending();

    }

    @Override
    public void act(final float delta) {
        // super.act(delta);
        for (Iterator<Action> iter = this.getActions().iterator(); iter.hasNext();) {
            iter.next().act(delta);
        }
    }

    public void addAnimal(MonkeyActor actor) {
        if (!this.iterator.hasNext()) {
            this.iterator = this.sectors.iterator();
        }
        BoatSector sec = this.iterator.next();
        sec.addAnimal(actor);
    }

    /**
     * Move to dodge an obstacle. Can dodge left or right based on direction.
     * @param direction this parameter determines direction. 1 is to the right, -1 is to the left.
     */
    public void move(float direction) {
        MoveToAction move = new MoveToAction();
        move.setPosition(this.getX() + (MOVE_DISTANCE * direction), this.getY());
        move.setDuration(3f);

        RotateToAction rot = new RotateToAction();
        rot.setRotation(30f);
        rot.setDuration(0f);
        ParallelAction par = new ParallelAction();
        // par.addAction(move);
        par.addAction(rot);

        this.addAction(par);

    }

}

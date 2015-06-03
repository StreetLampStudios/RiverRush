package nl.tudelft.ti2806.riverrush.graphics.entity;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.google.inject.Inject;

/**
 * Represents a boat that the animals row on.
 */
public class BoatGroup extends Group {

    private static final int SIZE = 600;

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

    private static final int NUM_SECTORS = 5;
    private static final int COL_COUNT = 5;
    private static final int ROW_COUNT = 2;

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
        this.sectors = new ArrayList<>();
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.WHITE);

        for (int i = 0; i < NUM_SECTORS; i++) {
            Color color = colors.get(i);
            BoatSector sec = new BoatSector(assetManager, ROW_COUNT, COL_COUNT, color);
            float secPosX = this.getX() + 50f;
            float secPosY = this.getY() + 50f + ((50f + sec.getHeight()) * i);
            sec.setPosition(secPosX, secPosY);
            this.sectors.add(sec);
            this.addActor(sec);
        }
        this.iterator = this.sectors.iterator();
        this.iterator.next(); // Start with the second sector

        // Make sectors(assetManager, rows, columns)
        // Set sector position

    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Texture tex = this.manager.get("data/ship.png", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, REGION_ENDX, REGION_ENDY);
        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
                this.getRotation());
        batch.setColor(Color.WHITE);
        this.drawChildren(batch, parentAlpha);
        batch.disableBlending();

    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    public void addAnimal(MonkeyActor actor) {
        if (!this.iterator.hasNext()) {
            this.iterator = this.sectors.iterator();
        }
        BoatSector sec = this.iterator.next();
        sec.addAnimal(actor);
    }

}

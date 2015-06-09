package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.google.inject.Inject;

import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

    private static final float MOVE_DISTANCE = 400;
    private static final double HITBOX_OFFSET = 0.3;

    private static final int NUM_SECTORS = 5;
    private static final int COL_COUNT = 5;
    private static final int ROW_COUNT = 2;

    private final Texture tex;
    private static final int MOVE_VOTE_THRESHOLD = 2;

    private final HashMap<AbstractAnimal, Integer> directionVotes;
    private int votingTotal = 0;
    private Circle bounds;

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

        this.setOriginX((this.getWidth() / 2));
        this.setOriginY((this.getHeight() / 2));

        this.sectors = new ArrayList<>();
        this.directionVotes = new HashMap<>();

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

        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
    }

    public void init() {
        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        v = this.localToStageCoordinates(v);

        this.bounds = new Circle(v.x, v.y, ((float) (this.getHeight() * HITBOX_OFFSET)));
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {

        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        this.localToStageCoordinates(v);

        this.bounds = new Circle(v.x, v.y, ((float) (this.getHeight() * HITBOX_OFFSET)));

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(this.tex, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
                this.getRotation(), 0, 0, this.tex.getWidth(), this.tex.getHeight(), false, false);

        batch.setColor(Color.WHITE);

        super.draw(batch, parentAlpha);
        batch.disableBlending();

    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    public void addAnimal(final AnimalActor actor) {
        if (!this.iterator.hasNext()) {
            this.iterator = this.sectors.iterator();
        }
        BoatSector sec = this.iterator.next();
        sec.addAnimal(actor);
    }

    public void voteForDirection(final AbstractAnimal animal, final int direction) {
        Integer currentVote = this.directionVotes.get(animal);
        if (currentVote == null || currentVote != direction) {
            this.votingTotal += direction;
            this.directionVotes.put(animal, direction);
            if (this.votingTotal <= -MOVE_VOTE_THRESHOLD) {
                this.move(-1);
            } else if (this.votingTotal >= MOVE_VOTE_THRESHOLD) {
                this.move(1);
            }
        }
    }

    /**
     * Move to dodge an obstacle. Can dodge left or right based on direction.
     *
     * @param direction this parameter determines direction. 1 is to the right, -1 is to the left.
     */
    public void move(final int direction) {
        MoveToAction move = new MoveToAction();
        move.setPosition(this.getX() + (MOVE_DISTANCE * direction), this.getY());

        move.setDuration(0.5f);
        move.setInterpolation(new Interpolation.Elastic(2, 1, 1, 0.3f));
        this.addAction(move);
    }

    public void removeAnimal(AnimalActor actor) {
        for (BoatSector sec : this.sectors) {
            if (sec.getAnimals().contains(actor)) {
                sec.removeActor(actor);
                sec.getAnimals().remove(actor);
            }
        }
    }

    public boolean calculateCollide() {
        return false;
    }

    public Circle getBounds() {
        return this.bounds;
    }
}

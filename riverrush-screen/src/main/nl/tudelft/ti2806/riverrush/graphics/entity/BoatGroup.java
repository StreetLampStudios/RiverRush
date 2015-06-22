package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.domain.entity.AbstractAnimal;
import nl.tudelft.ti2806.riverrush.domain.entity.Sector;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a boat that the animals row on.
 */
public class BoatGroup extends Group {

    private static final int BOAT_HEIGHT = 600;
    private static final int BOAT_WIDTH = 800;

    private final ArrayList<BoatSector> sectors;

    private final TextureRegion shipTexture;

    private static final float MOVE_DISTANCE = 200;
    private static final double HITBOX_OFFSET = 0.3;
    private static final float SECTOR_DIVIDING_DISTANCE = 32f;
    private static final float SECTOR_INIT_POS = 55f;

    private static final int COL_COUNT = 2;
    private static final int ROW_COUNT = 5;

    private final HashMap<AbstractAnimal, Integer> directionVotes;
    private static final float SECOND_SECTOR_OFFSET = 10f;
    private static final float LASTHALF_SECTOR_OFFSET = -12f;
    private static final float BOAT_MOVE_DURATION = 0.5f;
    private static final float INIT_WIDTH_OFFSET = 0.02f;
    private static final double STAGE_PARTITION = 0.45;
    private static final int SECTOR_WIDTH_PARTITION = 10;
    private static final int SECTOR_HEIGHT_PARTITION = -12;
    private int votingSum = 0;
    private float totalNumAnimals = 0;
    private Circle bounds;

    private MoveToAction move;

    private final float origY;

    /**
     * Creates an boat object with a given graphical representation.
     *
     * @param xpos   represents the position of the boat on the x axis
     * @param ypos   represents the position of the boat on the y axis
     * @param teamID - The id of the boat that this team will represent
     */
    @Inject
    public BoatGroup(final float xpos, final float ypos, final int teamID) {
        this.setX(xpos);
        this.setY(ypos - this.BOAT_HEIGHT / 2f);
        this.setWidth(this.BOAT_WIDTH);
        this.setHeight(this.BOAT_HEIGHT);

        this.origY = this.getY();
        if (teamID % 2 == 0) {
            this.shipTexture = Assets.monkeyShip;
        } else {
            this.shipTexture = Assets.raccoonShip;
        }

        this.setOriginX((this.getWidth() / 2));
        this.setOriginY((this.getHeight() / 2));

        this.sectors = new ArrayList<>();
        this.directionVotes = new HashMap<>();

        for (int i = Sector.countSectors() - 1; i >= 0; i--) {
            float extra = 0f;
            if (i == 2) {
                extra = SECOND_SECTOR_OFFSET;
            }
            if (i > 2) {
                extra = LASTHALF_SECTOR_OFFSET;
            }
            BoatSector sec = new BoatSector(ROW_COUNT, COL_COUNT);
            float secPosX = SECTOR_INIT_POS + ((SECTOR_DIVIDING_DISTANCE + sec.getWidth()) * i)
                    + extra;
            float secPosY = (this.getHeight() / 2) - (sec.getHeight() / 2);
            // float secPosX = (this.getWidth() / 2) - (sec.getWidth() / 2);
            // float secPosY = 50f + ((20f + sec.getHeight()) * i);
            sec.setPosition(secPosX, secPosY);
            this.sectors.add(sec);
            this.addActorAt(1, sec);
        }

        this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
    }

    /**
     * Init the boat.
     */
    public void init() {
        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        v = this.localToStageCoordinates(v);

        this.bounds = new Circle(v.x, v.y, ((float) (this.getHeight() * HITBOX_OFFSET)));
        this.move = new MoveToAction();
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        v = this.localToStageCoordinates(v);
        this.bounds.setPosition(v.x, v.y);

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(this.shipTexture, this.getX(), this.getY(), this.getOriginX(),
                this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(),
                this.getScaleY(), this.getRotation());

        batch.setColor(Color.WHITE);

        super.draw(batch, parentAlpha);
        batch.disableBlending();
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    /**
     * Vote for a direction to move the boat there.
     *
     * @param animal    The animal that has voted
     * @param direction The direction the animal voted in
     */
    public void voteForDirection(final AbstractAnimal animal, final int direction) {
        Integer currentVote = this.directionVotes.getOrDefault(animal, 0);
        if (currentVote != direction) {
            this.votingSum += currentVote;
            this.votingSum -= direction;
            this.directionVotes.put(animal, direction);
            this.updateBoatPosition();

            this.directionVotes.put(animal, direction);
        }
    }

    /**
     * Update the position of the boat.
     */
    private void updateBoatPosition() {
        float moveOffset = 0;
        if (this.totalNumAnimals > 0) {
            moveOffset = (this.votingSum / this.totalNumAnimals) * MOVE_DISTANCE;
        }
        float newY = this.origY + moveOffset;

        this.clearActions();
        this.move = new MoveToAction();
        this.move.setPosition(this.getX(), newY);

        this.move.setDuration(BOAT_MOVE_DURATION);
        this.addAction(this.move);
    }

    /**
     * Add an animal to teh boat.
     *
     * @param actor  The actor of the animal
     * @param sector The sector to add the animal in
     */
    public void addAnimal(final AnimalActor actor, final Sector sector) {

        BoatSector sec = this.sectors.get(sector.getIndex());
        if (sec.contains(actor)) {
            return;
        }
        sec.addAnimal(actor);

        this.totalNumAnimals++;
        this.updateBoatPosition();
    }

    /**
     * Remove an animal from the boat.
     *
     * @param aAnimal The animal to remove
     */
    public void removeAnimal(final AbstractAnimal aAnimal) {
        Animal anim = (Animal) aAnimal;
        AnimalActor actor = anim.getActor();
        for (BoatSector sec : this.sectors) {
            if (sec.contains(actor)) {
                sec.removeActor(actor);
                sec.remove(actor);
            }
        }
        this.totalNumAnimals--;
        this.directionVotes.remove(anim);
        this.updateBoatPosition();
    }

    /**
     * Determines whether the boat collides with a given object.
     *
     * @param obstacle refers to the rock for which collision has to be detected.
     * @return true if the collision occurs, else false.
     */
    public boolean isColliding(final Circle obstacle) {
        return Intersector.overlaps(obstacle, this.bounds);
    }

    /**
     * Return the child which collides with the given object.
     *
     * @param collider refers to the object for which collision has to be detected.
     * @return the animal for which collision occurs.
     */
    public AnimalActor getCollidingChild(final Circle collider) {
        AnimalActor result = null;
        for (BoatSector sector : this.sectors) {
            if (sector.isColliding(collider)) {
                result = sector.getCollidingChild(collider);
            }
        }

        return result;
    }

    /**
     * Resize the boat based on the given screen resolution.
     *
     * @param width  refers to the new base width.
     * @param height refers to the new base height.
     */
    public void resize(final int width, final int height) {
        this.setWidth((int) (width / (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / BOAT_WIDTH)));
        this.setHeight((int) (height
                / (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / BOAT_HEIGHT)));
        this.setX(width * INIT_WIDTH_OFFSET);
        this.setY((float) ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                * STAGE_PARTITION) - (this.getHeight() / 2));

        int i = 0;
        for (BoatSector sec : this.sectors) {
            sec.resize(width, height);

            float extra = 0f;
            if (i == 2) {
                extra = (float) width
                        / ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / SECTOR_WIDTH_PARTITION);
            }
            if (i > 2) {
                extra = (float) width / ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()
                        / SECTOR_HEIGHT_PARTITION);
            }

            float secPosX = (width / ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()
                    / SECTOR_INIT_POS)) + (((width / ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()
                    / SECTOR_DIVIDING_DISTANCE)) + sec.getWidth()) * i) + extra;
            float secPosY = (this.getHeight() / 2) - (sec.getHeight() / 2);

            sec.setPosition(secPosX, secPosY);

            i++;
        }
        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        v = this.localToStageCoordinates(v);
        this.bounds.setPosition(v.x, v.y);
    }
}

package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.awt.*;

/**
 * Boat sector class.
 */
public class BoatSector extends Group {

    private static final int START_POSITION = 2;

    private final int rowCount;
    private final int colCount;
    private static final int MONKEY_WIDTH = 50;
    private static final int MONKEY_HEIGHT = 90;
    private int currentAnimalPosition;
    private AnimalActor[] animals;
    private final Rectangle bounds;

    /**
     * Constructor.
     *
     * @param rows - number of rows
     * @param cols - number of columns
     */
    public BoatSector(final int rows, final int cols) {
        this.rowCount = rows;
        this.colCount = cols;

        this.setWidth(this.colCount * MONKEY_WIDTH); // Monkey width
        this.setHeight(this.rowCount * MONKEY_HEIGHT); // Monkey height

        Vector2 v = this.localToStageCoordinates(new Vector2(0, 0));
        this.bounds = new Rectangle(v.x, v.y, this.getWidth(), this.getHeight());

        this.currentAnimalPosition = START_POSITION;
        this.animals = new AnimalActor[this.colCount * this.rowCount];
        for (int i = 0; i < (this.colCount * this.rowCount); i++) {
            this.animals[i] = null;
        }
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Vector2 v = this.localToStageCoordinates(new Vector2(0, 0));
        this.bounds.setPosition(v.x, v.y);

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        super.draw(batch, parentAlpha);
        batch.disableBlending();
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    /**
     * Adds an animal to this sector.
     *
     * @param actor - the animal you want to add
     */
    public void addAnimal(final AnimalActor actor) {
        this.animals[this.currentAnimalPosition] = actor;
        float xPos = ((this.currentAnimalPosition % this.colCount) * actor.getWidth());
        float yPos = ((this.currentAnimalPosition / this.colCount) * actor.getHeight());
        actor.setPosition(xPos, yPos);
        this.currentAnimalPosition = (this.currentAnimalPosition + 2)
                % (this.rowCount * this.colCount);

        if (this.animals[this.currentAnimalPosition] != null) {
            this.currentAnimalPosition--;
        }
        this.addActor(actor);
    }

    /**
     * Returns the animals in this sector.
     *
     * @return the animals
     */
    public AnimalActor[] getAnimals() {
        return this.animals;
    }

    /**
     * Determines whether the sector collides with a given object.
     *
     * @param obstacle refers to the obstacle for which collision has to be detected.
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
        for (AnimalActor actor : this.animals) {
            if (actor != null && actor.isColliding(collider)) {
                return actor;
            }
        }
        return null;
    }

    /**
     * Resize the sector based on the given screen resolution.
     *
     * @param width  refers to the new base width.
     * @param height refers to the new base height.
     */
    public void resize(final int width, final int height) {
        this.setWidth(this.colCount * (float) (width
                / ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / MONKEY_WIDTH)));
        this.setHeight(this.rowCount * (float) (height
                / ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / MONKEY_HEIGHT)));

        for (AnimalActor actor : animals) {
            if (actor == null) {
                continue;
            }

            actor.resize(width, height);
            float xPos = ((this.currentAnimalPosition % this.colCount) * actor.getWidth());
            float yPos = ((this.currentAnimalPosition / this.colCount) * actor.getHeight());
            actor.setPosition(xPos, yPos);
        }
    }

    /**
     * Determines whether this sector contains a given actor.
     *
     * @param actor refers to the actor which needs to be found.
     * @return true if the sector contains it, else false.
     */
    public boolean contains(final AnimalActor actor) {
        for (AnimalActor s : animals) {
            if (s == actor) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the given actor from the sector.
     *
     * @param actor refers to the actor that needs to be removed.
     */
    public void remove(final AnimalActor actor) {
        for (int i = 0; i < animals.length; i++) {
            if (animals[i] == actor) {
                animals[i] = null;
                return;
            }
        }
    }
}

package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

import java.util.ArrayList;

public class BoatSector extends Group {

    private final static int START_POSITION = 2;

    private final int rowCount;
    private final int colCount;
    private int currentAnimalPosition;
    private ArrayList<AnimalActor> animals;
    private final Rectangle bounds;

    public BoatSector(int rows, int cols, Color color) {
        this.rowCount = rows;
        this.colCount = cols;

        this.setWidth(this.colCount * 50); // Monkey width
        this.setHeight(this.rowCount * 90); // Monkey height

        Vector2 v = this.localToStageCoordinates(new Vector2(0, 0));
        this.bounds = new Rectangle(v.x, v.y, this.getWidth(), this.getHeight());

        this.setColor(color);

        this.currentAnimalPosition = START_POSITION;
        this.animals = new ArrayList<>();
        for (int i = 0; i < (this.colCount * this.rowCount); i++) {
            this.animals.add(null);
        }
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Vector2 v = this.localToStageCoordinates(new Vector2(0, 0));
        this.bounds.setPosition(v.x, v.y);

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(Assets.boatSector, this.getX(), this.getY(), this.getOriginX(),
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

    public void addAnimal(AnimalActor actor) {
        this.animals.set(this.currentAnimalPosition, actor);
        float xPos = ((this.currentAnimalPosition % this.colCount) * actor.getWidth());
        float yPos = ((this.currentAnimalPosition / this.colCount) * actor.getHeight());
        actor.setPosition(xPos, yPos);
        this.currentAnimalPosition = (this.currentAnimalPosition + 2)
                % (this.rowCount * this.colCount);

        if (this.animals.get(this.currentAnimalPosition) != null) {
            this.currentAnimalPosition--;
        }
        this.addActor(actor);
    }

    /**
     * Returns the animals in this sector.
     *
     * @return the animals
     */
    public ArrayList<AnimalActor> getAnimals() {
        return this.animals;
    }

    public boolean isColliding(final Circle obstacle) {
        return Intersector.overlaps(obstacle, this.bounds);
    }

    public AnimalActor getCollidingChild(final Circle collider) {
        AnimalActor result = null;
        for (AnimalActor actor : this.animals) {
            if (actor != null && actor.isColliding(collider)) {
                return actor;
            }
        }
        return result;
    }
}

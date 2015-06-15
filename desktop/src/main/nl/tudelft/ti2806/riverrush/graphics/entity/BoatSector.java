package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

import java.util.ArrayList;

/**
 * Boat sector class.
 */
public class BoatSector extends Group {

    private static final int START_POSITION = 2;

    private final int rowCount;
    private final int colCount;
    private int currentAnimalPosition;
    private ArrayList<AnimalActor> animals;

    /**
     * Constructor.
     * @param rows - number of rows
     * @param cols - number of columns
     */
    public BoatSector(final int rows, final int cols) {
        this.rowCount = rows;
        this.colCount = cols;

        this.setWidth(this.colCount * 50); // Monkey width
        this.setHeight(this.rowCount * 90); // Monkey height

        this.currentAnimalPosition = START_POSITION;
        this.animals = new ArrayList<>();
        for (int i = 0; i < (this.colCount * this.rowCount); i++) {
            this.animals.add(null);
        }
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.draw(Assets.boatSector, this.getX(), this.getY(), this.getOriginX(),
                this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(),
                this.getScaleY(), this.getRotation());
        super.draw(batch, parentAlpha);
        batch.disableBlending();
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    /**
     * Adds an animal to this sector.
     * @param actor - the animal you want to add
     */
    public void addAnimal(final AnimalActor actor) {
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

}

package nl.tudelft.ti2806.riverrush.graphics.entity;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public class BoatSector extends Group {

    private final static int START_POSITION = 2;

    private final int rowCount;
    private final int colCount;
    private final AssetManager assetManager;
    private int currentAnimalPosition;
    private ArrayList<MonkeyActor> animals;

    public BoatSector(AssetManager assets, int rows, int cols, Color color) {
        this.rowCount = rows;
        this.colCount = cols;

        this.setWidth(this.colCount * 90); // Monkey width
        this.setHeight(this.rowCount * 50); // Monkey height

        this.setColor(color);

        this.assetManager = assets;
        this.currentAnimalPosition = START_POSITION;
        this.animals = new ArrayList<>();
        for (int i = 0; i < (this.colCount * this.rowCount); i++) {
            this.animals.add(null);
        }
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Texture tex = this.assetManager.get("data/sector.png", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, 700, 360);
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
        this.animals.set(this.currentAnimalPosition, actor);
        float xPos = this.getX()
                + ((this.currentAnimalPosition % this.colCount) * actor.getWidth());
        float yPos = this.getY()
                + ((this.currentAnimalPosition / this.colCount) * actor.getHeight());
        actor.setPosition(xPos, yPos);
        this.currentAnimalPosition = (this.currentAnimalPosition + 2)
                % (this.rowCount * this.colCount);

        if (this.animals.get(this.currentAnimalPosition) != null) {
            this.currentAnimalPosition--;
        }
        this.addActor(actor);
    }
}

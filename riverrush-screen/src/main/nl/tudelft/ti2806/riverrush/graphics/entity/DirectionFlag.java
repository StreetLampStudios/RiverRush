package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

/**
 * This flag belongs to an animal and indicates the given animal's voting position.
 */
public class DirectionFlag extends Actor {
    private static final float FLAG_WIDTH = 24;
    private static final float FLAG_HEIGHT = 48;

    /**
     * Creates a monkey object that represents player characters.
     *
     */
    @Inject
    public DirectionFlag() {
        this.setWidth(FLAG_WIDTH);
        this.setHeight(FLAG_HEIGHT);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(Assets.flag, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
                this.getRotation());

        batch.setColor(Color.WHITE);
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

}

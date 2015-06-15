package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

public class DirectionFlag extends Actor {
    private static final float FLAG_WIDTH = 32;
    private static final float FLAG_HEIGHT = 64;

    /**
     * Creates a monkey object that represents player characters.
     *
     */
    @Inject
    public DirectionFlag() {
        this.setWidth(FLAG_WIDTH);
        this.setHeight(FLAG_HEIGHT);

        // this.setOriginX(0);
        // this.setOriginY(0);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.draw(Assets.flag, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
                this.getRotation());

        batch.setColor(Color.WHITE);

        batch.disableBlending();
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

}

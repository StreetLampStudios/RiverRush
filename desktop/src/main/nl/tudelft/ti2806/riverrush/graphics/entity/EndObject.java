package nl.tudelft.ti2806.riverrush.graphics.entity;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public abstract class EndObject extends Actor {

    private final static float WIDTH = 384; // 768
    private final static float HEIGHT = 216; // 432
    private final static float X = 960 - (WIDTH / 2);
    private final static float Y = 540 - (HEIGHT / 2);
    protected TextureRegion region;

    public EndObject(AssetManager assetManager) {
        this.setPosition(X, Y);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);

        this.addAction(sequence(
                fadeOut(0f),
                parallel(Actions.scaleBy(2f, 2f, 1f), fadeIn(1f),
                        moveTo(X - (WIDTH), Y - (HEIGHT), 1f))));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(this.region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
                this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
                this.getRotation());

        batch.setColor(Color.WHITE);
        batch.disableBlending();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
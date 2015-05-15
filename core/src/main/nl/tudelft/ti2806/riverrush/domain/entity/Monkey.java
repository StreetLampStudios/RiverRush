package nl.tudelft.ti2806.riverrush.domain.entity;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.google.inject.Inject;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Monkey">Tutorial on monkeys.</a>
 */
public class Monkey extends AbstractAnimal {

    private final static float JUMP_HEIGHT = 30;
    private AssetManager manager;

    @Inject
    public Monkey(AssetManager assetManager, float x, float y, float w, float h) {
        this.manager = assetManager;
        this.setX(x);
        this.setY(y);
        this.setWidth(w);
        this.setHeight(h);

        MoveToAction jumpUp = new MoveToAction();
        jumpUp.setPosition(this.getX(), this.getY() + JUMP_HEIGHT);
        jumpUp.setDuration(0.1f);

        MoveToAction down = new MoveToAction();
        down.setPosition(this.getX(), y);
        down.setDuration(0.05f);

        SequenceAction seq = sequence(jumpUp, down);
        DelayAction delay = delay(1f, seq);
        RepeatAction rep = forever(delay);

        this.addAction(rep);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Texture tex = this.manager
                .get("assets/data/raccoon.png", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, 432, 432);
        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.draw(region, this.getX(), this.getY(), this.getOriginX(),
                this.getOriginY(), this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(), this.getRotation());
        batch.disableBlending();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

}

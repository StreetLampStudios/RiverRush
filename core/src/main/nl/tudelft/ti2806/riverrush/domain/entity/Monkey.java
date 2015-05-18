package nl.tudelft.ti2806.riverrush.domain.entity;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.google.inject.Inject;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Monkey">Tutorial on monkeys.</a>
 */
public class Monkey extends AbstractAnimal {

    private final static float JUMP_HEIGHT = 30;
    private AssetManager manager;
    private float origX;
    private float origY;

    @Inject
    public Monkey(AssetManager assetManager, float x, float y, float w, float h) {
        this.manager = assetManager;
        this.setX(x);
        this.setY(y);
        this.setWidth(w);
        this.setHeight(h);

        this.origX = x;
        this.origY = y;

        MoveToAction jumpUp = new MoveToAction();
        jumpUp.setPosition(this.getX(), this.getY() + JUMP_HEIGHT);
        jumpUp.setDuration(0.1f);

        MoveToAction jumpDown = new MoveToAction();
        jumpDown.setPosition(this.getX(), y);
        jumpDown.setDuration(0.05f);

        SequenceAction jump = sequence(jumpUp, jumpDown);

        AlphaAction fadeOut = fadeOut(1f);
        AlphaAction fadeIn = fadeIn(1f);

        SequenceAction fading = sequence(fadeOut, fadeIn);

        ColorAction red = Actions.color(Color.RED);
        ColorAction white = Actions.color(Color.WHITE);
        DelayAction colDel = delay(0.15f, white);

        SequenceAction blink = sequence(delay(0.15f, red), colDel);
        RepeatAction blinking = Actions.repeat(3, blink);

        SequenceAction mv = sequence(jump, fading, blinking);
        DelayAction delay = delay(1f, mv);
        RepeatAction rep = forever(delay);

        this.addAction(rep);

        // this.addAction(this.getHit());
        // this.addAction(sequence(this.getHit(), this.returnToBoat()));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Texture tex = this.manager
                .get("assets/data/raccoon.png", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, 432, 432);

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(region, this.getX(), this.getY(), this.getOriginX(),
                this.getOriginY(), this.getWidth(), this.getHeight(),
                this.getScaleX(), this.getScaleY(), this.getRotation());

        // batch.setColor(color.r, color.g, color.b, 1f);
        batch.setColor(Color.WHITE);

        batch.disableBlending();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    public Action getHit() {
        MoveToAction fall = new MoveToAction();
        fall.setPosition(this.getX() + 200, this.getY() - 520);
        fall.setDuration(0.5f);

        AlphaAction fade = Actions.fadeOut(0.5f);

        return Actions.parallel(fade, fall);
    }

    public Action returnToBoat() {
        MoveToAction fall = new MoveToAction();
        fall.setPosition(this.origX, this.origY);

        VisibleAction fade = Actions.show();

        return Actions.parallel(fade, fall);

    }

}

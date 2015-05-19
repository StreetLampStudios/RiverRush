package nl.tudelft.ti2806.riverrush.domain.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.google.inject.Inject;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Monkey">Tutorial on monkeys.</a>
 */
public class Monkey extends AbstractAnimal {

    private static final float JUMP_HEIGHT = 30;
    private static final int END_REGIONX = 432;
    private static final int END_REGIONY = 432;
    private static final int FALL_DISTANCEX = 200;
    private static final int FALL_DISTANCEY = -520;
    private static final float FALL_DURATION = 0.5f;
    private static final float JUMP_UP_DURATION = 0.1f;
    private static final float JUMP_DOWN_DURATION = 0.05f;

    private AssetManager manager;
    private float origX;
    private float origY;

    /**
     * Creates a monkey object that represents player characters.
     *
     * @param assetManager enables the object to retrieve its assets
     * @param xpos         represents the position of the monkey on the x axis
     * @param ypos         represents the position of the monkey on the y axis
     * @param width        represents the width of the monkey object
     * @param height       represents the height of the monkey object
     */
    @Inject
    public Monkey(AssetManager assetManager, float xpos, float ypos, float width, float height) {
        this.manager = assetManager;
        this.setX(xpos);
        this.setY(ypos);
        this.setWidth(width);
        this.setHeight(height);

        this.origX = xpos;
        this.origY = ypos;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Texture tex = this.manager.get("assets/data/raccoon.png", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, END_REGIONX, END_REGIONY);

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        batch.draw(region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
            this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());

        batch.setColor(Color.WHITE);

        batch.disableBlending();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    /**
     * Creates an action that represents getting hit graphically (falling off the boat).
     *
     * @return an action that can be added to the actor
     */
    public Action getHit() {
        MoveToAction fall = new MoveToAction();
        fall.setPosition(this.getX() + FALL_DISTANCEX, this.getY() + FALL_DISTANCEY);
        fall.setDuration(FALL_DURATION);

        AlphaAction fade = Actions.fadeOut(FALL_DURATION);

        return Actions.parallel(fade, fall);
    }

    /**
     * Creates an action that represents returning to the boat graphically.
     *
     * @return an action that can be added to the actor
     */
    public Action returnToBoat() {
        MoveToAction fall = new MoveToAction();
        fall.setPosition(this.origX, this.origY);

        VisibleAction fade = Actions.show();

        return Actions.parallel(fade, fall);

    }

    @Override
    public Action jump() {
        this.setState(this.getState().jump());
        MoveToAction jumpUp = new MoveToAction();
        jumpUp.setPosition(this.getX(), this.getY() + JUMP_HEIGHT);
        jumpUp.setDuration(JUMP_UP_DURATION);

        MoveToAction jumpDown = new MoveToAction();
        jumpDown.setPosition(this.getX(), this.origY);
        jumpDown.setDuration(JUMP_DOWN_DURATION);

        SequenceAction jump = sequence(jumpUp, jumpDown);

        return jump;
    }

}

package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Game object representing a monkey.
 */
public class AnimalActor extends Group {

    /**
     * Specifies the animal's width.
     */
    private static final float ANIMAL_WIDTH = 90; // 144
    /**
     * Specifies the animal's height.
     */
    private static final float ANIMAL_HEIGHT = 50; // 81

    private static final float JUMP_HEIGHT = 100;
    private static final int FALL_DISTANCEX = 200;
    private static final int FALL_DISTANCEY = -520;
    private static final float FALL_VELOCITY = 0.5f;
    private static final float JUMP_UP_DURATION = 0.3f;
    private static final float JUMP_DOWN_DURATION = 0.15f;
    private static final float DELAY_DURATION = 0.5f;
    private static final float WIGGLE_DURATION = 0.5f;
    private static final float WIGGLE_BACK_DURATION = 0.125f;
    private static final float WIGGLE_RIGHT_DURATION = 0.25f;
    private static final float WIGGLE_LEFT_DURATION = 0.125f;
    private static final float WIGGLE_DISTANCE = 5f;
    private static final double HITBOX_MULTIPLIER = 0.3;

    private static final float ROLL_DURATION = 0.7f;

    private float origX;
    private float origY;
    private Circle bounds;

    private DirectionFlag directionFlag;

    /**
     * Creates a monkey object that represents player characters.
     *
     * @param dispatcher   Event dispatcher for dispatching events
     */
    @Inject
    public AnimalActor(final EventDispatcher dispatcher) {
        this.setWidth(ANIMAL_WIDTH);
        this.setHeight(ANIMAL_HEIGHT);

        this.setOriginX(this.getWidth() / 2);
        this.setOriginY(this.getHeight() / 2);

        DirectionFlag flag = new DirectionFlag();
        flag.setPosition(this.getWidth(), this.getHeight() / 2);

        flag.setVisible(false);
        this.addActor(flag);
        this.directionFlag = flag;
    }

    /**
     * Initialise the Actor.
     */
    public void init() {
        Vector2 v = new Vector2(this.getOriginX(), this.getOriginY());
        v = this.localToStageCoordinates(v);

        this.bounds = new Circle(v.x, v.y, ((float) (this.getHeight() * HITBOX_MULTIPLIER)));
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        Color color = this.getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        this.localToStageCoordinates(v);

        this.bounds = new Circle(v.x, v.y, ((float) (this.getHeight() * HITBOX_MULTIPLIER)));

        batch.enableBlending();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.draw(Assets.raccoon, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
            this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(),
            this.getRotation());

        batch.setColor(Color.WHITE);
        super.draw(batch, parentAlpha);
        batch.disableBlending();
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
    }

    /**
     * Creates an action that represents getting hit graphically (falling off the boat).
     *
     * @return an action that can be added to the actor
     */
    public Action collideAction() {
        MoveToAction fall = new MoveToAction();
        fall.setPosition(this.getX() + FALL_DISTANCEX, this.getY() + FALL_DISTANCEY);
        fall.setDuration(FALL_VELOCITY);

        // AlphaAction fade = Actions.fadeOut(FALL_VELOCITY);
        AlphaAction fade = new AlphaAction();
        fade.setAlpha(0f);
        fade.setDuration(FALL_VELOCITY);

        return Actions.parallel(fade, fall);
    }

    /**
     * Create action to move back on the boat.
     *
     * @return Action to return
     */
    public Action returnMove() {
        MoveToAction ret = new MoveToAction();
        ret.setPosition(this.origX, this.origY);
        return ret;
    }

    /**
     * Create action to fade.
     *
     * @return Action to fade
     */
    public Action returnFade() {
        AlphaAction fade = new AlphaAction();
        fade.setAlpha(1f);
        fade.setDuration(0f);
        return fade;
    }

    /**
     * Creat action to roll.
     *
     * @return Action to roll.
     */
    public Action rollAction(final Direction direction) {
        MoveToAction roll = new MoveToAction();
        roll.setDuration(ROLL_DURATION);
        roll.setPosition((direction == Direction.LEFT ? -1 * this.getWidth() : this.getParent()
            .getWidth()), this.getY());
        RotateByAction rot = new RotateByAction();
        rot.setDuration(ROLL_DURATION); // 0.5f
        rot.setAmount(360f * (direction == Direction.LEFT ? 1 : -1));

        return Actions.parallel(roll, rot);
    }

    /**
     * Updates the flag that the animal should hold.
     *
     * @param direction - The direction it wants to go
     */
    public void updateFlag(final Direction direction) {
        this.directionFlag.setRotation(-30f);
        this.directionFlag.setVisible(true);
        if (direction == Direction.RIGHT) {
            this.setScale(1f, 1f);
            this.directionFlag.setColor(Color.RED);
        } else {
            this.setScale(-1, 1);
            this.directionFlag.setColor(Color.GREEN);
        }
        RotateToAction rot = new RotateToAction();
        rot.setRotation(0f);
        rot.setDuration(0.3f);
        this.directionFlag.addAction(rot);
    }

    public Action jumpAction() {
        MoveToAction jumpUp = new MoveToAction();
        jumpUp.setPosition(this.getX(), this.getY() + JUMP_HEIGHT);
        jumpUp.setDuration(JUMP_UP_DURATION);

        MoveToAction drop = new MoveToAction();
        drop.setPosition(this.getX(), this.origY);
        drop.setDuration(JUMP_DOWN_DURATION);

        this.setOrigin((this.getWidth() / 2), (this.getHeight() / 2));

        RotateByAction wiggleLeft = Actions.rotateBy(WIGGLE_DISTANCE);
        wiggleLeft.setDuration(WIGGLE_LEFT_DURATION);

        RotateByAction wiggleRight = Actions.rotateBy(-(WIGGLE_DISTANCE * 2));
        wiggleRight.setDuration(WIGGLE_RIGHT_DURATION);

        RotateByAction wiggleBack = Actions.rotateBy(WIGGLE_DISTANCE);
        wiggleBack.setDuration(WIGGLE_BACK_DURATION);

        SequenceAction wiggle = sequence(wiggleLeft, wiggleRight, wiggleBack);

        SequenceAction jump = sequence(jumpUp,
            Actions.repeat((int) (DELAY_DURATION / WIGGLE_DURATION), wiggle), drop);

        return jump;
    }

    @Override
    public void setPosition(final float x, final float y) {
        super.setPosition(x, y);
        this.origX = x;
        this.origY = y;
    }

    public void moveAlong(float direction, float distance) {
        MoveToAction move = new MoveToAction();
        move.setPosition(this.getX() + (distance * direction), this.getY());
        move.setDuration(3f);
        this.addAction(move);
    }

    /**
     * @return the hitbox of the animal.
     */
    public Circle getBounds() {
        return this.bounds;
    }

}

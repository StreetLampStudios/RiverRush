package nl.tudelft.ti2806.riverrush.graphics.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.domain.event.Direction;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

import java.awt.*;

/**
 * Game object representing a monkey.
 */
public class AnimalActor extends Group {

    /**
     * Specifies the animal's width.
     */
    private static final float ANIMAL_WIDTH = 50; // 144
    /**
     * Specifies the animal's height.
     */
    private static final float ANIMAL_HEIGHT = 90; // 81
    private static final float FLAG_ROTATION = 30f;
    private static final float FLAG_UPDATE_DURATION = 0.3f;

    private TextureRegion animalTexture;
    private static final float JUMP_HEIGHT = 20;
    private static final int FALL_DISTANCEX = -520;
    private static final int FALL_DISTANCEY = 200;
    private static final float FALL_VELOCITY = 0.5f;
    private static final float JUMP_UP_DURATION = 0.3f;
    private static final float JUMP_DOWN_DURATION = 0.15f;
    private static final float DELAY_DURATION = 1f;
    private static final double HITBOX_MULTIPLIER = 0.3;
    private static final float FLAG_OFFSET = 0.8f;
    private static final float JUMP_SCALING = 1.5f;


    private float origX;
    private float origY;
    private Circle bounds;

    private final DirectionFlag directionFlag;
    private Animal animal;
    private final ShadowActor shadow;

    /**
     * Creates a monkey object that represents player characters.
     */
    @Inject
    public AnimalActor() {
        this.setWidth(ANIMAL_WIDTH);
        this.setHeight(ANIMAL_HEIGHT);

        this.setOriginX(this.getWidth() / 2);
        this.setOriginY(this.getHeight() / 2);

        DirectionFlag flag = new DirectionFlag();
        flag.setPosition(this.getWidth() / 2, this.getHeight() * FLAG_OFFSET);

        flag.setVisible(false);
        this.addActor(flag);
        this.directionFlag = flag;
        ShadowActor shad = new ShadowActor();

        this.shadow = shad;
    }

    /**
     * Change the size of the animal actor to scale with the given resolution of the screen.
     * @param width the new base width.
     * @param height the new base height.
     */
    public void resize(final int width, final int height) {
        this.setWidth(width / ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / ANIMAL_WIDTH));
        this.setHeight(height
            / ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / ANIMAL_HEIGHT));
        this.shadow.rezize(width, height);
        Vector2 v = new Vector2(this.getWidth() / 2, this.getHeight() / 2);
        this.localToStageCoordinates(v);

        this.bounds = new Circle(v.x, v.y, ((float) (this.getHeight() * HITBOX_MULTIPLIER)));
    }

    /**
     * This class displays a shadow for the animal actor to indicate position of the animal on the z-axis.
     */
    private static class ShadowActor extends Actor {

        private float origX;
        private float origY;
        private static final float WIDTH = 40;
        private static final float HEIGHT = 72;

        /**
         * Constructs an shadow for the given animal based on the animal's height and width.
         */
        public ShadowActor() {
            this.setWidth(WIDTH);
            this.setHeight(HEIGHT);
            this.setOrigin(this.getWidth() / 2, this.getHeight() / 2);
        }

        /**
         * Resize the shadow based on the given screen size.
         * @param width the new base width the shadow should be scaled to.
         * @param height the new base height the shadow should be scaled to.
         */
        public void rezize(final int width, final int height) {
        	this.setWidth(width / ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / WIDTH));
        	this.setHeight(height
                / ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / HEIGHT));
        }

        @Override
        public void draw(final Batch batch, final float parentAlpha) {
            batch.draw(Assets.shadow, this.getX(), this.getY(), this.getOriginX(),
                    this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(),
                    this.getScaleY(), this.getRotation());
            super.draw(batch, parentAlpha);
        }

        @Override
        public void act(final float delta) {
            super.act(delta);
        }

        /**
         * Update the shadow based on the animal's jump.
         */
        private void update() {
            ScaleToAction scaleUp = new ScaleToAction();
            scaleUp.setScale(JUMP_SCALING);
            scaleUp.setDuration(JUMP_UP_DURATION);

            ScaleToAction scaleDown = new ScaleToAction();
            scaleDown.setScale(1f);
            scaleDown.setDuration(JUMP_DOWN_DURATION);

            SequenceAction jump = new SequenceAction(scaleUp, Actions.delay(DELAY_DURATION,
                    scaleDown));

            this.addAction(jump);
        }
    }

    /**
     * Initialise the Actor.
     */
    public void init() {
        Vector2 v = new Vector2(this.getOriginX(), this.getOriginY());
        v = this.localToStageCoordinates(v);

        this.bounds = new Circle(v.x, v.y, ((float) (this.getHeight() * HITBOX_MULTIPLIER)));

        if (this.animal.getTeamId() % 2 == 0) {
            this.animalTexture = Assets.monkeyMap.get(this.animal.getVariation());
        } else {
            this.animalTexture = Assets.raccoonMap.get(this.animal.getVariation()); //
        }
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

        super.draw(batch, parentAlpha);
        this.shadow.draw(batch, parentAlpha);

        batch.draw(this.animalTexture, this.getX(), this.getY(), this.getOriginX(),
                this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(),
                this.getScaleY(), this.getRotation());

        batch.setColor(Color.WHITE);

        batch.disableBlending();
    }

    @Override
    public void act(final float delta) {
        super.act(delta);
        this.shadow.act(delta);
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

        AlphaAction fade = new AlphaAction();
        fade.setAlpha(0f);
        fade.setDuration(FALL_VELOCITY);

        this.shadow.origX = this.shadow.getX();
        this.shadow.origY = this.shadow.getY();
        this.shadow.addAction(Actions.moveTo(this.shadow.getX() + FALL_DISTANCEX,
                this.shadow.getY() + FALL_DISTANCEY, FALL_VELOCITY));

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

        MoveToAction returnShad = new MoveToAction();
        returnShad.setPosition(this.shadow.origX, this.shadow.origY);

        this.shadow.addAction(returnShad);
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
     * Updates the flag that the animal should hold.
     *
     * @param direction - The direction it wants to go
     */
    public void updateFlag(final Direction direction) {
        this.directionFlag.setVisible(true);
        if (direction == Direction.LEFT) {
            this.directionFlag.setPosition(this.getWidth() / 2, this.getHeight() * FLAG_OFFSET);
            this.directionFlag.setRotation(FLAG_ROTATION);
            this.directionFlag.setScale(1f, 1f);
            this.directionFlag.setColor(Color.GREEN);
        } else {
            this.directionFlag.setPosition(this.getWidth() / 2, this.getHeight()
                    * (1 - FLAG_OFFSET));
            this.directionFlag.setRotation(-1 * FLAG_ROTATION);
            this.directionFlag.setScale(1, -1);
            this.directionFlag.setColor(Color.RED);
        }

        RotateToAction rot = new RotateToAction();
        rot.setRotation(0f);
        rot.setDuration(FLAG_UPDATE_DURATION);
        this.directionFlag.addAction(rot);
    }

    /**
     * Adds an action to the animal which displays itself as a jump.
     */
    public void jumpAction() {
        MoveToAction jumpUp = new MoveToAction();
        jumpUp.setPosition(this.getX(), this.getY() + JUMP_HEIGHT);
        jumpUp.setDuration(JUMP_UP_DURATION);

        ScaleToAction scaleUp = new ScaleToAction();
        scaleUp.setScale(JUMP_SCALING);
        scaleUp.setDuration(JUMP_UP_DURATION);

        MoveToAction drop = new MoveToAction();
        drop.setPosition(this.origX, this.getY());
        drop.setDuration(JUMP_DOWN_DURATION);

        ScaleToAction scaleDown = new ScaleToAction();
        scaleDown.setScale(1f);
        scaleDown.setDuration(JUMP_DOWN_DURATION);

        ParallelAction parUp = new ParallelAction();
        parUp.addAction(jumpUp);
        parUp.addAction(scaleUp);

        ParallelAction parDown = new ParallelAction();
        parDown.addAction(drop);
        parDown.addAction(scaleDown);

        this.setOrigin((this.getWidth() / 2), (this.getHeight() / 2));

        SequenceAction jump = new SequenceAction(parUp, Actions.delay(DELAY_DURATION, parDown));

        this.addAction(jump);
        this.shadow.update();
    }

    @Override
    public void setPosition(final float x, final float y) {
        super.setPosition(x, y);
        this.origX = x;
        this.origY = y;

        float xS = this.getX() + (this.getWidth() / 2) - (this.shadow.getWidth() / 2);
        float yS = this.getY() + (this.getHeight() / 2) - (this.shadow.getHeight() / 2);
        this.shadow.setPosition(xS, yS);
    }

    /**
     * @return the hitbox of the animal.
     */
    public Circle getBounds() {
        return this.bounds;
    }

    public void setAnimal(final Animal anim) {
        this.animal = anim;
    }

    public Animal getAnimal() {
        return this.animal;
    }

    /**
     * Determines whether the animal collides with an object.
     * @param obstacle the given obstacle for which the collision has to be determined.
     * @return true when collision occurs, else false.
     */
    public boolean isColliding(final Circle obstacle) {
        return Intersector.overlaps(obstacle, this.bounds);
    }
}

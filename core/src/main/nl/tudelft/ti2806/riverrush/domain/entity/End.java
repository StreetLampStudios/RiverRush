package nl.tudelft.ti2806.riverrush.domain.entity;

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

public abstract class End extends Actor {

  private static final float WIDTH = 384;
  private static final float HEIGHT = 216;
  private static final float X = 960 - (WIDTH / 2);
  private static final float Y = 540 - (HEIGHT / 2);
  private static final float SCALE = 2f;
  private static final float ANIMATION_DURATION = 1f;
  protected TextureRegion region;

  /**
   * Creates an end object (either win or lose) that can be spawned upon reaching the end of the
   * game.
   *
   * @param assetManager
   *          enables the object to retrieve its assets
   */
  public End(AssetManager assetManager) {
    this.setPosition(X, Y);
    this.setWidth(WIDTH);
    this.setHeight(HEIGHT);

    this.addAction(sequence(
        fadeOut(0f),
        parallel(Actions.scaleBy(SCALE, SCALE, ANIMATION_DURATION), fadeIn(ANIMATION_DURATION),
            moveTo(X - (WIDTH), Y - (HEIGHT), ANIMATION_DURATION))));
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.enableBlending();
    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    Color color = this.getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

    batch.draw(this.region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(),
        this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());

    batch.setColor(Color.WHITE);
    batch.disableBlending();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

}

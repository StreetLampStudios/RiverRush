package nl.tudelft.ti2806.Graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import nl.tudelft.ti2806.riverrush.domain.entity.Obstacle;

/**
 * Created by Martijn on 18-5-2015.
 */
public class ObstacleGraphic extends Actor implements Obstacle {

  private final AssetManager assets;
  private final static float SIZE = 256;

  public ObstacleGraphic(AssetManager assets, double offset) {
    this.assets = assets;
    setWidth(256);
    setHeight((float) (256.0 * 1080.0 / 1920.0) / 2);
    setPosition((float)(800.0 + 320.0 * offset) - SIZE/2, 1080);


    MoveToAction moveDown = new MoveToAction();
    moveDown.setPosition(960 - SIZE / 2, -2 * SIZE);
    moveDown.setDuration(3f);

    addAction(moveDown);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    Texture tex = this.assets.get("assets/data/cannonball.png", Texture.class);
    TextureRegion region = new TextureRegion(tex, 0, 0, 512, 512);
    batch.enableBlending();
    batch.draw(region, this.getX(), this.getY(), this.getOriginX(),
      this.getOriginY(), this.getWidth(), this.getHeight(),
      this.getScaleX(), this.getScaleY(), this.getRotation());
  }

  public boolean isDone(){
    if(getY() == -2*SIZE){
      return true;
    }
    return false;
  }
}

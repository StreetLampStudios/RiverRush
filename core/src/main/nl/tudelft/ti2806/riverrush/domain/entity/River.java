package nl.tudelft.ti2806.riverrush.domain.entity;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.google.inject.Inject;

public class River extends Actor {

    private AssetManager manager;

    // private float xPos;
    // private float yPos;
    // private float WIDTH;
    // private float HEIGHT;
    private float mid;

    @Inject
    public River(AssetManager assetManager, float y, float w, float h,
            boolean left) {
        this.manager = assetManager;
        // this.yPos = y;
        // this.xPos = x;
        // this.WIDTH = w;
        // this.HEIGHT = h;

        // this.setX(x);
        // this.setY(y);
        // this.setY(h);
        int offset = left ? 192 : 0;
        this.setPosition(offset, h);
        this.setWidth(w);
        this.setHeight(h);
        // this.mid = (w + 192) / 2 - offset;
        this.mid = w / 2 + offset;

        MoveToAction moveDown = new MoveToAction();
        moveDown.setPosition(offset, y);
        moveDown.setDuration(1f);

        MoveToAction moveUp = new MoveToAction();
        moveUp.setPosition(offset, h * -1);

        SequenceAction seq = sequence(moveUp, moveDown);
        RepeatAction rep = forever(seq);

        this.addAction(rep);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // super.draw(batch, parentAlpha);
        // batch.draw(this.manager.get("assets/data/river.jpg", Texture.class),
        // this.xPos, this.yPos, this.WIDTH, this.HEIGHT);
        Texture tex = this.manager.get("assets/data/river.jpg", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, 570, 570);
        batch.draw(region, this.getX(), this.getY(), this.getOriginX(),
                this.getOriginY(), this.getWidth(), this.getHeight() * 2,
                this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public float getMid() {
        return this.mid;
    }
}

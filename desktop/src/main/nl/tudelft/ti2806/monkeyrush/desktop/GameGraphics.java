package nl.tudelft.ti2806.monkeyrush.desktop;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameGraphics {

    protected float x, y;
    protected int width, height;
    protected Texture texture;
    protected boolean removed = false;
    protected Rectangle rectangle;
    protected float speed;
    protected Sprite sprite;
    protected boolean rotates = false;
    protected float rotation = 0;
    protected Random random = new Random();

    // protected Texture testure = Assets.manager.get("data/test.jpg",
    // Texture.class);
    protected Texture boat = Assets.manager.get("data/boat.jpg", Texture.class);

    public GameGraphics(String imageFile, int x, int y) {
        this.texture = Assets.manager.get(imageFile, Texture.class);
        this.sprite = new Sprite(this.texture);
        this.rectangle = new Rectangle();
        this.x = x;
        this.y = y;
    }

    public GameGraphics(int y) {
        this.y = y;
    }

    public GameGraphics(int x, int y) {
        this.rectangle = new Rectangle();
        this.x = x;
        this.y = y;
    }

    public void update() {
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

}

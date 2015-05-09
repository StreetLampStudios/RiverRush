package nl.tudelft.ti2806.monkeyrush;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Shared application class.
 */
public class Main extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    ShapeRenderer shapemaker;
    double d = 0;

    public int width;
    
    @Override
    public void create () {

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        shapemaker = new ShapeRenderer();
    }

    @Override
    public void render () {
        int width = Gdx.graphics.getWidth();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        batch.draw(img, 0, 0);
//        batch.end();
        shapemaker.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl20.glLineWidth((float) 10.0);
        shapemaker.setColor(1, 1, 1, 1);
        drawStatus();
        shapemaker.rect(250, 150, 450, 750);
        shapemaker.rect(1150, 150, 450, 750);
        shapemaker.end();
    }

    public void drawStatus(){
        if(d < 100){
            d+=0.3;
        }
        Status.getStatus().setPointsTeam1(d);
        Status.getStatus().setPointsTeam2(d * 1.1);
        Status.getStatus().draw(shapemaker);
    }
}

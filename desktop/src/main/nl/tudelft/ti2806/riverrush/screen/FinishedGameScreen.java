package nl.tudelft.ti2806.riverrush.screen;

import nl.tudelft.ti2806.riverrush.desktop.MainDesktop;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * State for a finished game.
 */
public class FinishedGameScreen implements Screen {

    private Stage stage;

    /**
     * Creates the graphical representation of the finished game screen.
     *
     * @param assetManager refers to the manager that has made all loaded assets available for use.
     * @param eventDispatcher is the dispatcher that handles all relevant events.
     */
    public FinishedGameScreen(final AssetManager assetManager, final EventDispatcher eventDispatcher) {
        // Does not need to do anything yet
    }

    @Override
    public void show() {
        this.stage = new Stage();

        Texture texture = new Texture(Gdx.files.internal("data/end.jpg"));
        TextureRegion region = new TextureRegion(texture, 0, 0, MainDesktop.getWidth(),
                MainDesktop.getHeight());

        Image image = new Image(region);
        image.setFillParent(true);
        this.stage.addActor(image);

    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act();
        this.stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
        // Does not need to do anything yet
    }

    @Override
    public void pause() {
        // Does not need to do anything yet
    }

    @Override
    public void resume() {
        // Does not need to do anything yet
    }

    @Override
    public void hide() {
        // Does not need to do anything yet
    }

    @Override
    public void dispose() {
        // Does not need to do anything yet
    }
}

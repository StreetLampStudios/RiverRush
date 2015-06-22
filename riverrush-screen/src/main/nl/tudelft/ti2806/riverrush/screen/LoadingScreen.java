package nl.tudelft.ti2806.riverrush.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import nl.tudelft.ti2806.riverrush.domain.event.AssetsLoadedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.Assets;

import java.io.IOException;

/**
 * Creates the graphical representation of the loading game screen. The loading game screen simply
 * shows an image to indicate that the game is loading.
 */
public class LoadingScreen implements Screen {

    private final EventDispatcher dispatcher;
    private Stage stage;
    private Image loadingImage;

    /**
     * Creates the graphical representation of the loading game screen. The loading game screen
     * simply shows an image to indicate that the game is loading.
     *
     * @param eventDispatcher is the dispatcher that handles all relevant events.
     */
    public LoadingScreen(final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
    }

    /**
     * When a LoadingScreen is created by the dependency injector, It automatically calls this
     * constructor.
     */

    @Override
    public void show() {
        this.stage = new Stage();

        this.loadingImage = Assets.getLoadingImage();
        this.loadingImage.setPosition(0, 0);
        this.loadingImage.setFillParent(true);
        this.stage.addActor(this.loadingImage);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act();
        this.stage.draw();

        try {
            Assets.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dispatcher.dispatch(new AssetsLoadedEvent());
    }

    @Override
    public void resize(final int width, final int height) {
        this.loadingImage.setSize(width, height);
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

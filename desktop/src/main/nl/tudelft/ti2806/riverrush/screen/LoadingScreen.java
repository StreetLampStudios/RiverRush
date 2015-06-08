package nl.tudelft.ti2806.riverrush.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import nl.tudelft.ti2806.riverrush.domain.event.AssetsLoadedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

/**
 * Creates the graphical representation of the loading game screen. The loading game screen simply
 * shows an image to indicate that the game is loading.
 */
public class LoadingScreen implements Screen {

    private static final int TEXTURE_WIDTH = 1920;
    private static final int TEXTURE_HEIGHT = 1080;
    private final EventDispatcher dispatcher;
    private Stage stage;

    private final AssetManager assets;

    /**
     * Creates the graphical representation of the loading game screen. The loading game screen
     * simply shows an image to indicate that the game is loading.
     *
     * @param assetManager    refers to the manager that has made all loaded assets available for use.
     * @param eventDispatcher is the dispatcher that handles all relevant events.
     */
    public LoadingScreen(final AssetManager assetManager, final EventDispatcher eventDispatcher) {
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;
    }

    /**
     * When a LoadingScreen is created by the dependency injector, It automatically calls this
     * constructor.
     */

    @Override
    public void show() {

        this.assets.finishLoading();

        this.stage = new Stage();

        Texture texture = new Texture(Gdx.files.internal("data/loading.jpeg"));
        TextureRegion region = new TextureRegion(texture, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        Image image = new Image(region);
        image.setFillParent(true);
        this.stage.addActor(image);

        // Load all images/textures that we will use
        this.assets.load(getFileName("end.jpg"), Texture.class);
        this.assets.load(getFileName("sector.png"), Texture.class);
        this.assets.load(getFileName("ship.png"), Texture.class);
        this.assets.load(getFileName("raccoon.png"), Texture.class);
        this.assets.load(getFileName("grass.jpg"), Texture.class);
        this.assets.load(getFileName("field.jpg"), Texture.class);
        this.assets.load(getFileName("river.png"), Texture.class);
        this.assets.load(getFileName("cannonball.png"), Texture.class);
        this.assets.load(getFileName("iceberg.png"), Texture.class);
        this.assets.load(getFileName("win.png"), Texture.class);
        this.assets.load(getFileName("lose.png"), Texture.class);
        this.assets.load(getFileName("bootje.jpg"), Texture.class);
        this.assets.load(getFileName("wood-floor.jpg"), Texture.class);
        this.assets.load(getFileName("dividingLine.png"), Texture.class);

    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // If all the assets have been correctly loaded, go to the next screen
        if (this.assets.update()) {
            this.dispatcher.dispatch(new AssetsLoadedEvent());
        }

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

    /**
     * Returns the filename in proper formatting.
     *
     * @param name the name of the file.
     * @return the name of the file with "data/" added to it.
     */
    public static String getFileName(final String name) {
        return "data/" + name;
    }

}

package nl.tudelft.ti2806.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class LoadingScreen extends AbstractScreen {

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;

    private final AssetManager assets;
    private final RiverGame game;
    private final Provider<WaitingScreen> scrnProvider;

    public static String getFileName(String name) {
        return "assets/data/" + name;
    }

    @Inject
    public LoadingScreen(final AssetManager assetManager,
            final Provider<RiverGame> provider, final Provider<WaitingScreen> screenProvider) {
        this.assets = assetManager;
        this.game = provider.get();
        this.scrnProvider = screenProvider;
    }

    /**
     * When a LoadingScreen is created by the dependency injector, It
     * automatically calls this constructor.
     */

    @Override
    public void show() {

        this.assets.finishLoading();

        this.stage = new Stage();
        this.atlas = new TextureAtlas("assets/uiskin.atlas");
        this.skin = new Skin(Gdx.files.internal("assets/uiskin.json"),
                this.atlas);

        Texture texture = new Texture(
                Gdx.files.internal("assets/data/loading.jpeg"));
        TextureRegion region = new TextureRegion(texture, 0, 0, 1920, 1080);

        Image image = new Image(region);
        image.setFillParent(true);
        this.stage.addActor(image);

        //Load all images/textures that we will use
        this.assets.load(getFileName("boat.jpg"), Texture.class);
        this.assets.load(getFileName("shipv2.png"), Texture.class);
        this.assets.load(getFileName("ship.png"), Texture.class);
        this.assets.load(getFileName("raccoon.png"), Texture.class);
        this.assets.load(getFileName("pirateship.png"), Texture.class);
        this.assets.load(getFileName("left.jpg"), Texture.class);
        this.assets.load(getFileName("grass.jpg"), Texture.class);
        this.assets.load(getFileName("river.jpg"), Texture.class);
        this.assets.load(getFileName("cannonball.png"), Texture.class);
        this.assets.load(getFileName("win.png"), Texture.class);
        this.assets.load(getFileName("lose.png"), Texture.class);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //If all the assets have been correctly loaded, go to the next screen
        if (this.assets.update()) {
            this.game.setScreen(this.scrnProvider.get());
        }

        this.stage.act();
        this.stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}

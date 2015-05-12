package nl.tudelft.ti2806.monkeyrush.desktop;

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


    protected Stage stage;
    protected TextureAtlas atlas;
    protected Skin skin;

    private final AssetManager assetManager;
    private final RiverGame game;

  @Inject
  public LoadingScreen(AssetManager assetManager, Provider<RiverGame> provider) {
    this.assetManager = assetManager;
    this.game = provider.get();
  }

  /**
   * When a LoadingScreen is created by the dependency injector,
   * It automatically calls this constructor.
   */


  @Override
    public void show() {

        this.assetManager.finishLoading();

        this.stage = new Stage();
        this.atlas = new TextureAtlas("assets/uiskin.atlas");
        this.skin = new Skin(Gdx.files.internal("assets/uiskin.json"), this.atlas);

        Texture texture = new Texture(Gdx.files.internal("assets/data/loading.jpeg"));
        TextureRegion region = new TextureRegion(texture, 0, 0, 1920, 1080);

        Image image = new Image(region);
        image.setFillParent(true);
        this.stage.addActor(image);

        this.assetManager.load("assets/data/boat.jpg", Texture.class);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (this.assetManager.update()) {
            this.game.setScreen(new GameScreen(this.game));
        }

        this.stage.act();
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        this.assetManager.unload("assets/data/loading.jpeg");

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}

package nl.tudelft.ti2806.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GameScreen extends AbstractScreen {

    private SideStage leftScreen;
    private SideStage rightScreen;
    private CenterStage midScreen;

    private Stage leftStage;
    private Stage midStage;
    private Stage rightStage;
    public OrthographicCamera camera;
    private static int WIDTH = 1920;
    private static int HEIGHT = 1080;
    private RiverGame game;
    private AssetManager assets;

    @Inject
    public GameScreen(Provider<RiverGame> provider, AssetManager assets) {
        this.leftScreen = new SideStage(assets, WIDTH, HEIGHT, true);
        this.rightScreen = new SideStage(assets, WIDTH, HEIGHT, false);
        this.midScreen = new CenterStage(assets, WIDTH, HEIGHT);

        this.leftStage = new Stage();
        this.midStage = new Stage();
        this.rightStage = new Stage();

        this.leftStage.addActor(this.leftScreen);
        this.rightStage.addActor(this.rightScreen);
        this.midStage.addActor(this.midScreen);

        this.assets = assets;
        this.game = provider.get();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, WIDTH, HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.game.getBatch().setProjectionMatrix(this.camera.combined);

        this.leftStage.act(Gdx.graphics.getDeltaTime());
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / 20 * 9,
                Gdx.graphics.getHeight());
        this.leftStage.draw();

        this.midStage.act(Gdx.graphics.getDeltaTime());
        Gdx.gl.glViewport(Gdx.graphics.getWidth() / 20 * 9, 0,
                Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight());
        this.midStage.draw();

        this.rightStage.act(Gdx.graphics.getDeltaTime());
        Gdx.gl.glViewport(Gdx.graphics.getWidth() / 20 * 11, 0,
                Gdx.graphics.getWidth() / 20 * 9, Gdx.graphics.getHeight());
        this.rightStage.draw();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        this.leftStage.dispose();
        this.rightStage.dispose();
        this.midStage.dispose();
        this.game.getBatch().dispose();

    }

}

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

    // private RunningGame runGame;
    private SideStage leftScreen;
    private SideStage rightScreen;
    private CenterStage midScreen;

    private Stage leftStage;
    private Stage midStage;
    private Stage rightStage;
    // private TextureAtlas atlas;
    // private Skin skin;
    public OrthographicCamera camera;
    private static int WIDTH = 1920;
    private static int HEIGHT = 1080;
    private RiverGame game;
    private AssetManager assets;

    @Inject
    public GameScreen(Provider<RiverGame> provider, AssetManager assets) {
        // this.runGame = runGame;
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

        // this.game.getBatch().begin();
        //
        // this.game.getBatch().end();
    }

    @Override
    public void show() {
        // this.atlas = new TextureAtlas("uiskin.atlas");
        // this.skin = new Skin(Gdx.files.internal("uiskin.json"), this.atlas);

        // Texture lefttexture = new Texture(
        // Gdx.files.internal("assets/data/left.jpg"));
        // TextureRegion leftregion = new TextureRegion(lefttexture, 0, 0, 472,
        // 455);

        // Texture midTex = new
        // Texture(Gdx.files.internal("assets/data/mid.jpg"));
        // TextureRegion midRegion = new TextureRegion(midTex, 0, 0, 275, 183);
        //
        // Texture righttexture = new Texture(
        // Gdx.files.internal("assets/data/right.jpg"));
        // TextureRegion rightregion = new TextureRegion(righttexture, 0, 0,
        // 540,
        // 540);

        // Image leftImage = new Image(leftregion);
        // leftImage.setFillParent(true);
        // this.leftStage.addActor(leftImage);
        //
        // Boat boat = new Boat(this.assets, WIDTH / 2, HEIGHT / 2, 200, 200,
        // this.game.getBatch());
        // River river = new River(this.assets, 192, 0, 1536, 1080);
        // this.leftStage.addActor(river);
        // this.leftStage.addActor(boat);

        // Image midImage = new Image(midRegion);
        // midImage.setFillParent(true);
        // this.midStage.addActor(midImage);
        //
        // Image rightImage = new Image(rightregion);
        // rightImage.setFillParent(true);
        // this.rightStage.addActor(rightImage);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {
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
    public void dispose() {
        this.leftStage.dispose();
        this.rightStage.dispose();
        this.midStage.dispose();
        this.game.getBatch().dispose();

    }

}

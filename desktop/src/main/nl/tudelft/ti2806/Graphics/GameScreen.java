package nl.tudelft.ti2806.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import nl.tudelft.ti2806.riverrush.desktop.DesktopLauncher;

@Singleton
public class GameScreen extends AbstractScreen {

    private int WIDTH = (int) DesktopLauncher.WIDTH;
    private int HEIGHT = (int) DesktopLauncher.HEIGHT;

    private SideStage leftScreen;
    private SideStage rightScreen;
    private CenterStage midScreen;

    private Stage leftStage;
    private Stage midStage;
    private Stage rightStage;
    private Stage banksLeft;
    private Stage banksRight;
    public OrthographicCamera camera;
    private RiverGame game;
    private AssetManager assets;

    @Inject
    public GameScreen(final Provider<RiverGame> provider, final AssetManager assetsManager) {

        this.assets = assetsManager;
        this.game = provider.get();

        this.banksLeft = new Stage();

        this.leftScreen = new SideStage(assets, WIDTH, HEIGHT);
        this.leftStage = new Stage();
        this.leftStage.addActor(this.leftScreen);

        this.midScreen = new CenterStage(assets, WIDTH, HEIGHT);
        this.midStage = new Stage();
        this.midStage.addActor(this.midScreen);

        this.rightScreen = new SideStage(assets, WIDTH, HEIGHT);
        this.rightStage = new Stage();
        this.rightStage.addActor(this.rightScreen);

        this.banksRight = new Stage();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, WIDTH, HEIGHT);

        //TODO temporary function calls to check functionality
        leftScreen.spawnObstacle(0.0);

        this.leftScreen.win();
        this.rightScreen.lose();
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.game.getBatch().setProjectionMatrix(this.camera.combined);

        drawLeftBanks();
        drawLeftStage();
        drawMidStage();
        drawRightStage();
        drawRightBanks();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /**
     * Draw left sandy beach.
     */
    public void drawLeftBanks() {
        this.banksLeft.act(Gdx.graphics.getDeltaTime());
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth() / 20, // 0 - 0.05
            Gdx.graphics.getHeight());
        this.banksLeft.draw();
    }

    /**
     * Draw right river with stuff.
     */
    public void drawLeftStage() {
        this.leftStage.act(Gdx.graphics.getDeltaTime());
        Gdx.gl.glViewport(Gdx.graphics.getWidth() / 20, 0, // 0.05 - 0.45
            Gdx.graphics.getWidth() / 5 * 2, Gdx.graphics.getHeight());
        this.leftStage.draw();
    }

    /**
     * Draw the progressbar.
     */
    public void drawMidStage() {
        this.midStage.act(Gdx.graphics.getDeltaTime());
        Gdx.gl.glViewport(Gdx.graphics.getWidth() / 20 * 9, 0, // 0.45 - 0.55
            Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight());
        this.midStage.draw();
    }

    /**
     * Draw left river with stuff.
     */
    public void drawRightStage() {
        this.rightStage.act(Gdx.graphics.getDeltaTime());
        Gdx.gl.glViewport(Gdx.graphics.getWidth() / 20 * 11, 0, // 0.55 - 0.95
            Gdx.graphics.getWidth() / 5 * 2, Gdx.graphics.getHeight());
        this.rightStage.draw();
    }

    /**
     * Draw left sandy beach.
     */
    public void drawRightBanks() {
        this.banksRight.act(Gdx.graphics.getDeltaTime());
        Gdx.gl.glViewport(Gdx.graphics.getWidth() / 20 * 19, 0, // 0.95 - 1
            Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight());
        this.banksRight.draw();
    }

    @Override
    public void show() {
        //Get texture
        Texture tex = this.assets.get("assets/data/grass.jpg", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, 229, 138);

        Image leftImg = new Image(region);
        leftImg.setFillParent(true);
        this.banksLeft.addActor(leftImg);

        Image rightImg = new Image(region);
        rightImg.setFillParent(true);
        this.banksRight.addActor(rightImg);
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

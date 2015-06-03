package nl.tudelft.ti2806.riverrush.screen;

import nl.tudelft.ti2806.riverrush.desktop.MainDesktop;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.TickHandler;
import nl.tudelft.ti2806.riverrush.graphics.CenterStage;
import nl.tudelft.ti2806.riverrush.graphics.SideStage;
import nl.tudelft.ti2806.riverrush.graphics.entity.BoatGroup;
import nl.tudelft.ti2806.riverrush.graphics.entity.ObstacleGraphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The playing game screen constructs and displays all the visuals that are required during game
 * play.
 */
@Singleton
public class PlayingGameScreen implements Screen {

    private final EventDispatcher dispatcher;
    private static final int WIDTH = MainDesktop.getWidth();
    private static final int HEIGHT = MainDesktop.getHeight();
    private static final double BANKSIZE = 0.05; // 1.0 / 20.0;
    private static final double SCREENSIZE = 0.4; // 1.0 / 5.0 * 2.0;
    private static final double MIDSIZE = 0.1; // 1.0 / 10.0;

    // Partioning
    private static final double FIRSTBANKEDGE = 0.05; // 1.0 / 20.0;
    private static final double LEFTSCREENEDGE = 0.45; // 1.0 / 20.0 * 9.0;
    private static final double MIDEDGE = 0.55; // 1.0 / 20.0 * 11.0;
    private static final double RIGHTSCREENEDGE = 0.95; // 1.0 / 20.0 * 19.0;

    private static final int ENDTEXTUREX = 103; // 229;
    private static final int ENDTEXTUREY = 314; // 138;

    private SideStage leftScreen;
    private SideStage rightScreen;
    private CenterStage midScreen;

    private Stage leftStage;
    private Stage midStage;
    private Stage rightStage;
    private Stage banksLeft;
    private Stage banksRight;
    private OrthographicCamera camera;
    private final AssetManager assets;
    private SpriteBatch spriteBatch;
    private TickHandler rightTickHandler;
    private TickHandler leftTickHandler;
    private TickHandler onTick;

    /**
     * Creates the graphical representation of the playing game screen. The playing game screen
     * shows the various stages that are relevant to the players including but not limited to the
     * river, boats, characters, and obstacles.
     *
     * @param assetManager refers to the manager that has made all loaded assets available for use.
     * @param eventDispatcher is the dispatcher that handles all relevant events.
     */
    @Inject
    public PlayingGameScreen(final AssetManager assetManager, final EventDispatcher eventDispatcher) {
        this.dispatcher = eventDispatcher;
        this.assets = assetManager;
    }

    /**
     * Initialises the stages, screens, and cameras.
     *
     * @param onTick - tickHandler for the screens
     */
    public void init(final TickHandler onTick) {
        this.onTick = onTick;
        this.banksLeft = new Stage();

        this.leftScreen = new SideStage(this.assets, WIDTH, HEIGHT, this.dispatcher);
        this.leftStage = new Stage();
        this.leftStage.addActor(this.leftScreen);

        this.midScreen = new CenterStage(this.assets, WIDTH, HEIGHT);
        this.midStage = new Stage();
        this.midStage.addActor(this.midScreen);

        this.rightScreen = new SideStage(this.assets, WIDTH, HEIGHT, this.dispatcher);
        this.rightStage = new Stage();
        this.rightStage.addActor(this.rightScreen);

        this.banksRight = new Stage();

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, WIDTH, HEIGHT);

        this.spriteBatch = new SpriteBatch();
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update();
        this.spriteBatch.setProjectionMatrix(this.camera.combined);

        this.drawLeftBanks();
        this.drawLeftStage();
        this.drawMidStage();
        this.drawRightStage();
        this.drawRightBanks();

        this.onTick.handle();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /**
     * Draw left sandy beach.
     */
    private void drawLeftBanks() {
        this.banksLeft.act(Gdx.graphics.getDeltaTime());
        int width = (int) (Gdx.graphics.getWidth() * BANKSIZE);
        Gdx.gl.glViewport(0, 0, width, // 0 - 0.05
                Gdx.graphics.getHeight());
        this.banksLeft.draw();
    }

    /**
     * Draw right river with stuff.
     */
    private void drawLeftStage() {
        this.leftStage.act(Gdx.graphics.getDeltaTime());
        int start = (int) (Gdx.graphics.getWidth() * FIRSTBANKEDGE);
        int width = (int) (Gdx.graphics.getWidth() * SCREENSIZE);
        Gdx.gl.glViewport(start, 0, // 0.05 - 0.45
                width, Gdx.graphics.getHeight());
        this.leftStage.draw();
    }

    /**
     * Draw the progressbar.
     */
    private void drawMidStage() {
        this.midStage.act(Gdx.graphics.getDeltaTime());
        int start = (int) (Gdx.graphics.getWidth() * LEFTSCREENEDGE);
        int width = (int) (Gdx.graphics.getWidth() * MIDSIZE);
        Gdx.gl.glViewport(start, 0, // 0.45 - 0.55
                width, Gdx.graphics.getHeight());
        this.midStage.draw();
    }

    /**
     * Draw left river with stuff.
     */
    private void drawRightStage() {
        this.rightStage.act(Gdx.graphics.getDeltaTime());
        int start = (int) (Gdx.graphics.getWidth() * MIDEDGE);
        int width = (int) (Gdx.graphics.getWidth() * SCREENSIZE);
        Gdx.gl.glViewport(start, 0, // 0.55 - 0.95
                width, Gdx.graphics.getHeight());
        this.rightStage.draw();
    }

    /**
     * Draw left sandy beach.
     */
    private void drawRightBanks() {
        this.banksRight.act(Gdx.graphics.getDeltaTime());
        int start = (int) (Gdx.graphics.getWidth() * RIGHTSCREENEDGE);
        int width = (int) (Gdx.graphics.getWidth() * BANKSIZE);
        Gdx.gl.glViewport(start, 0, // 0.95 - 1
                width, Gdx.graphics.getHeight());
        this.banksRight.draw();
    }

    @Override
    public void show() {
        // Get texture
        Texture tex = this.assets.get("data/field.jpg", Texture.class);
        TextureRegion region = new TextureRegion(tex, 0, 0, ENDTEXTUREX, ENDTEXTUREY);

        Image leftImg = new Image(region);
        leftImg.setFillParent(true);
        this.banksLeft.addActor(leftImg);

        Image rightImg = new Image(region);
        rightImg.setFillParent(true);
        this.banksRight.addActor(rightImg);
    }

    @Override
    public void resize(final int width, final int height) {
        // Does not need to do anything
    }

    @Override
    public void hide() {
        // Does not need to do anything
    }

    @Override
    public void pause() {
        // Does not need to do anything
    }

    @Override
    public void resume() {
        // Does not need to do anything
    }

    @Override
    public void dispose() {
        // Does not need to do anything
    }

    /**
     * adds an obstacle on the..
     *
     * @param isLeft - left or right side
     * @param graphic - where the obstacle is the graphic
     */
    public void addObstacle(boolean isLeft, ObstacleGraphic graphic) {
        if (isLeft) {
            this.leftScreen.spawnObstacle(graphic);
        } else {
            this.rightScreen.spawnObstacle(graphic);
        }
    }

    public void addTeam(final BoatGroup boat, final Integer teamID) {
        if (teamID == 0) { // TODO: Temporary hard coding
            this.leftScreen.addActor(boat);
        } else {
            this.rightScreen.addActor(boat);
        }

    }
}

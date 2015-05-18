package nl.tudelft.ti2806.Graphics;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class WaitingScreen extends AbstractScreen {

    protected Stage stage;
    private final AssetManager assetManager;
    private final RiverGame game;
    private final Provider<GameScreen> scrnProvider;

    private TextureAtlas atlas;
    private Skin skin;

    private Label timer;
    private Label counter;

    private Timer tmr;

    private int time;
    private int count;

    @Inject
    public WaitingScreen(AssetManager assetManager,
            Provider<RiverGame> provider, Provider<GameScreen> screenProvider) {
        this.assetManager = assetManager;
        this.game = provider.get();
        this.scrnProvider = screenProvider;
        this.time = Integer.MAX_VALUE;
    }

    @Override
    public void show() {
        this.atlas = new TextureAtlas("uiskin.atlas");
        this.skin = new Skin(Gdx.files.internal("uiskin.json"), this.atlas);
        this.stage = new Stage();

        Texture texture = new Texture(
                Gdx.files.internal("assets/data/loading.jpeg"));
        TextureRegion region = new TextureRegion(texture, 0, 0, 1920, 1080);

        Image image = new Image(region);
        image.setFillParent(true);
        this.stage.addActor(image);

        this.timer = new Label("Time till game start: ", this.skin);
        this.timer.setPosition(1200, 540);
        this.stage.addActor(this.timer);

        this.counter = new Label("Connected: ", this.skin);
        this.counter.setPosition(1200, 500);
        this.stage.addActor(this.counter);

        this.startTimer(5);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (this.time == 0) {
            this.tmr = null;
            this.game.setScreen(this.scrnProvider.get());
        }

        this.stage.act();
        this.stage.draw();

    }

    public void addConnection() {
        this.count++;
        this.counter.setText("Connected: " + this.count);

    }

    public void startTimer(int time) {
        this.tmr = new Timer();
        this.time = time;
        this.tmr.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                WaitingScreen.this.time--;
                WaitingScreen.this.timer.setText("Time till game start: "
                        + WaitingScreen.this.time);
                WaitingScreen.this.addConnection();

            }
        }, 1000, 1000);
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

    }

}

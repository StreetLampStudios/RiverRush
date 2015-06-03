package nl.tudelft.ti2806.riverrush.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import nl.tudelft.ti2806.riverrush.desktop.MainDesktop;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The waiting game screen displays the information that is required for players before the game has
 * started.
 */
public class WaitingScreen implements Screen {

    private static final int SECOND = 1000;
    private static final float TIMER_LABEL_WIDTH_MULTIPLIER = 0.625f;
    private static final float TIMER_LABEL_HEIGHT_MULTIPLIER = 0.5f;
    private static final float COUNTER_LABEL_WIDTH_MULTIPLIER = 0.625f;
    private static final float COUNTER_LABEL_HEIGHT_MULTIPLIER = 0.45f;
    private Stage stage;

    private TextureAtlas atlas;
    private Skin skin;

    // Label for time left till game start
    private Timer tmr;
    private Label timer;
    private int time;

    // Label for amount of people connected
    private Label counter;
    private int count;

    /**
     * Creates the graphical representation of the waiting game screen. This screen displays an
     * image to indicate the waiting screen, a timer for the time that remains before the game can
     * start, and a counter for the amount of currently connected players.
     *
     * @param assetManager    refers to the manager that has made all loaded assets available for use.
     * @param eventDispatcher is the dispatcher that handles all relevant events.
     */
    @Inject
    public WaitingScreen(final AssetManager assetManager, final EventDispatcher eventDispatcher) {
        this.time = Integer.MAX_VALUE;
    }

    @Override
    public void show() {
        this.atlas = new TextureAtlas("uiskin.atlas");
        this.skin = new Skin(Gdx.files.internal("uiskin.json"), this.atlas);
        this.stage = new Stage();

        Texture texture = new Texture(Gdx.files.internal("data/loading.jpeg"));
        TextureRegion region = new TextureRegion(texture, 0, 0, MainDesktop.getWidth(),
            MainDesktop.getHeight());

        Image image = new Image(region);
        image.setFillParent(true);
        this.stage.addActor(image);

        this.createTimerLabel();
        this.createCounterLabel();

    }

    /**
     * Creates a timerLabel.
     */
    private void createTimerLabel() {
        this.timer = new Label("Time till game start: ", this.skin);
        this.timer.setPosition(Gdx.graphics.getWidth() * TIMER_LABEL_WIDTH_MULTIPLIER,
            Gdx.graphics.getHeight() * TIMER_LABEL_HEIGHT_MULTIPLIER); // 1200, 540
        this.stage.addActor(this.timer);
    }

    /**
     * Creates the counter label.
     */
    private void createCounterLabel() {
        this.counter = new Label("Connected: ", this.skin);
        this.counter.setPosition(Gdx.graphics.getWidth() * COUNTER_LABEL_WIDTH_MULTIPLIER,
            Gdx.graphics.getHeight() * COUNTER_LABEL_HEIGHT_MULTIPLIER); // 1200, 500
        this.stage.addActor(this.counter);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act();
        this.stage.draw();

    }

    /**
     * Tell the waiting screen that someone has connected.
     */
    private void addConnection() {
        this.count++;
        this.counter.setText("Connected: " + this.count);
    }

    /**
     * Start counting down to zero. When zero is reached go to the next screen
     *
     * @param amountOfTime - amount of seconds to count down to
     */
    public void startTimer(final int amountOfTime) {
        this.tmr = new Timer();
        this.time = amountOfTime;
        this.tmr.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                WaitingScreen.this.time--;
                WaitingScreen.this.timer
                    .setText("Time till game start: " + WaitingScreen.this.time);
                // WaitingScreen.this.addConnection();

            }
        }, SECOND, SECOND);
    }

    @Override
    public void resize(final int width, final int height) {
        // Does not need to do anything yet
    }

    @Override
    public void hide() {
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
    public void dispose() {
        if (this.tmr != null) {
            this.tmr.cancel();
        }
    }

}

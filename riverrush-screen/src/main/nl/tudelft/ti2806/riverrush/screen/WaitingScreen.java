package nl.tudelft.ti2806.riverrush.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.inject.Inject;
import nl.tudelft.ti2806.riverrush.graphics.Assets;
import nl.tudelft.ti2806.riverrush.sound.Sound;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The waiting game screen displays the information that is required for players before the game has
 * started.
 */
public class WaitingScreen implements Screen {

    private static final int SECOND = 1000;
    private static final float TIMER_LABEL_WIDTH_MULTIPLIER = 0.6f;
    private static final float TIMER_LABEL_HEIGHT_MULTIPLIER = 0.8f;
    private static final float COUNTER_LABEL_WIDTH_MULTIPLIER = 0.6f;
    private static final float COUNTER_LABEL_HEIGHT_MULTIPLIER = 0.75f;
    private static final int DESKTOP_HEIGHT = 1920;
    private static final int DESKTOP_WIDTH = 1080;
    private static final int HEIGHT_DIVISION = 5;
    private static final int WIDTH_DIVISION = 2;
    private static final int SIZE = 300;
    private Stage stage;
    private Skin skin;

    // Label for time left till game start
    private Timer tmr;
    private Label timer;
    private int time;

    // Label for amount of people connected
    private Label counter;
    private Image image;

    private Sound sound;

    /**
     * Creates the graphical representation of the waiting game screen. This screen displays an
     * image to indicate the waiting screen, a timer for the time that remains before the game can
     * start, and a counter for the amount of currently connected players.
     */
    @Inject
    public WaitingScreen() {
        this.time = Integer.MAX_VALUE;
    }

    @Override
    public void show() {
        TextureAtlas atlas = new TextureAtlas("uiskin.atlas");
        this.skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
        this.stage = new Stage();

        this.image = new Image(Assets.ark);
        this.image.setPosition(0, 0);
        this.image.setFillParent(true);
        this.stage.addActor(this.image);

        Image qr = new Image(Assets.qr);
        qr.setPosition(DESKTOP_HEIGHT / HEIGHT_DIVISION, (DESKTOP_WIDTH
                / WIDTH_DIVISION) - (qr.getWidth() / 2));
        qr.setSize(SIZE, SIZE);
        this.stage.addActor(qr);

        this.createTimerLabel();
        this.createCounterLabel();

        this.sound = Assets.waitingMusic;
        this.sound.loop();
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
        this.counter = new Label("Waiting until both teams have at least one player", this.skin);
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
            }
        }, SECOND, SECOND);
    }

    @Override
    public void resize(final int width, final int height) {
        this.timer.setFontScale(Gdx.graphics.getWidth() / width, Gdx.graphics.getHeight() / height);
        this.counter.setFontScale(Gdx.graphics.getWidth() / width, Gdx.graphics.getHeight() / height);
        this.image.setSize(width, height);
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
        this.sound.stop();
    }

    /**
     * Switch the label to the ready state.
     */
    public void setLabelToReady() {
        this.counter.setText("Enough players have joined the game. The game will start soon");
    }
}

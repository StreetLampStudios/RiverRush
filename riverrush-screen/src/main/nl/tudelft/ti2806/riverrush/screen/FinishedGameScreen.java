package nl.tudelft.ti2806.riverrush.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.graphics.Assets;
import nl.tudelft.ti2806.riverrush.sound.Sound;

import java.util.Timer;
import java.util.TimerTask;

/**
 * State for a finished game.
 */
public class FinishedGameScreen implements Screen {

    private Skin skin;
    private TextureAtlas atlas;
    private Stage stage;
    private Label timerLabel;
    private static final float TIMER_LABEL_WIDTH_MULTIPLIER = 0.43f;
    private static final float TIMER_LABEL_HEIGHT_MULTIPLIER = 0.2f;
    private static final float WINNING_LABEL_HEIGHT_MULTIPLIER = 0.3f;
    private static final float WINNING_LABEL_WIDTH_MULTIPLIER = 0.40f;
    private static final int TIMER_TICK = 1000;
    private int countdown;
    private Image image;
    private Label teamWonLabel;

    private Sound sound;


    /**
     * Creates the graphical representation of the finished game screen.
     *
     * @param dispatcher is the dispatcher that handles all relevant events.
     */
    public FinishedGameScreen(final EventDispatcher dispatcher) {
        Gdx.app.postRunnable(() -> {
            atlas = new TextureAtlas("uiskin.atlas");
            skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);
        });
    }

    @Override
    public void show() {
        this.stage = new Stage();

        this.sound = Assets.cheeringSound;
        this.sound.play();
    }

    /**
     * Create image for the winning team.
     *
     * @param winningID The id of the team that won
     */
    private void createImage(final int winningID) {
        if (winningID % 2 == 0) {
            this.image = new Image(Assets.monkeyParty);
        } else {
            this.image = new Image(Assets.raccoonParty);
        }

        this.image.setPosition(0, 0);
        this.image.setFillParent(true);
        this.stage.addActor(image);
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act();
        this.stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
        if (this.timerLabel != null) {
            this.timerLabel.setFontScale(Gdx.graphics.getWidth() / width, Gdx.graphics.getHeight() / height);
        }

        if (this.teamWonLabel != null) {
            this.teamWonLabel.setFontScale(Gdx.graphics.getWidth()
                    / width, Gdx.graphics.getHeight() / height);
        }

        if (this.image != null) {
            this.image.setSize(width, height);
        }
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
        this.sound.stop();
    }

    /**
     * Creates a timerLabel.
     */
    private void createLabels() {
        Gdx.app.postRunnable(() -> {
            timerLabel = new Label("", skin);
            timerLabel.setFontScale(2f);
            timerLabel.setPosition(Gdx.graphics.getWidth() * TIMER_LABEL_WIDTH_MULTIPLIER,
                    Gdx.graphics.getHeight() * TIMER_LABEL_HEIGHT_MULTIPLIER); // 1200, 540
            stage.addActor(timerLabel);
        });

    }

    /**
     * Starts the countdown to restart the game.
     *
     * @param time in milliseconds
     */
    public void startCountdown(final int time) {
        this.countdown = time / TIMER_TICK;
        createLabels();
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                if (countdown == 0) {
                    tmr.cancel();
                    return;
                }
                if (timerLabel != null) {
                    timerLabel.setText("Returning to lobby in: " + countdown);
                }
                countdown--;
            }
        }, 0, TIMER_TICK);
    }

    /**
     * Show the winning team on the finished game screen.
     *
     * @param winningID the ID of the winning team.
     */
    public void drawWinningLabel(final int winningID) {

        String winningTeamName;
        if (winningID % 2 == 0) {
            winningTeamName = "Monkey";
        } else {
            winningTeamName = "Raccoon";
        }

        teamWonLabel = new Label("Team " + winningTeamName + " won! Congratulations!", skin);
        teamWonLabel.setFontScale(2f);
        teamWonLabel.setPosition(Gdx.graphics.getWidth() * WINNING_LABEL_WIDTH_MULTIPLIER,
                Gdx.graphics.getHeight() * WINNING_LABEL_HEIGHT_MULTIPLIER); // 1200, 540

        this.createImage(winningID);

        stage.addActor(teamWonLabel);
    }
}

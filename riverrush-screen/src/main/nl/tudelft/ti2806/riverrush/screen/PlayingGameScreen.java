package nl.tudelft.ti2806.riverrush.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import nl.tudelft.ti2806.riverrush.game.TickHandler;
import nl.tudelft.ti2806.riverrush.graphics.Assets;
import nl.tudelft.ti2806.riverrush.graphics.CenterStage;
import nl.tudelft.ti2806.riverrush.graphics.SideStage;
import nl.tudelft.ti2806.riverrush.graphics.entity.BoatGroup;
import nl.tudelft.ti2806.riverrush.graphics.entity.CannonBallGraphic;
import nl.tudelft.ti2806.riverrush.graphics.entity.RockGraphic;
import nl.tudelft.ti2806.riverrush.graphics.entity.Team;
import nl.tudelft.ti2806.riverrush.sound.Sound;

/**
 * The playing game screen constructs and displays all the visuals that are required during game
 * play.
 */
public class PlayingGameScreen implements Screen {

    private static final double RIVER_SIZE = 0.475;
    private static final double MID_SIZE = 0.05;
    private static final float BOAT_X_OFFSET = 0.02f;

    private SideStage riverLeft;
    private SideStage riverRight;
    private CenterStage betweenRivers;

    private final TickHandler onTick;
    private int height;
    private int width;

    private Sound sound;

    /**
     * Creates the graphical representation of the playing game screen. The playing game screen
     * shows the various stages that are relevant to the players including but not limited to the
     * river, boats, characters, and obstacles.
     *
     * @param tickHandler handles game ticks.
     */
    public PlayingGameScreen(final TickHandler tickHandler) {
        this.onTick = tickHandler;
    }

    @Override
    public void show() {
        this.riverLeft = new SideStage();
        this.betweenRivers = new CenterStage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.riverRight = new SideStage();

        this.height = Gdx.graphics.getHeight();
        this.width = Gdx.graphics.getWidth();

        this.sound = Assets.backgroundMusic;
        this.sound.loop();
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        double startPosition = 0.0;

        this.drawStage(this.riverLeft, startPosition, RIVER_SIZE);
        startPosition += RIVER_SIZE;

        this.drawStage(this.betweenRivers, startPosition, MID_SIZE);
        startPosition += MID_SIZE;

        this.drawStage(this.riverRight, startPosition, RIVER_SIZE);

        this.onTick.handle();
    }

    /**
     * Draws a stage on the screen. Each stage occupies the entire width of the screen.
     *
     * @param toDraw             The stage to draw.
     * @param positionPercentage The position of the stage from the left. Between 0 and 1.
     * @param heightPercentage   The width of the stage between 0 and 1. (1 = 100%).
     */
    private void drawStage(final Stage toDraw, final double positionPercentage,
                           final double heightPercentage) {

        int yPos = (int) ((double) this.height * positionPercentage);
        int viewPortHeight = (int) ((double) this.height * heightPercentage);
        Gdx.gl.glViewport(0, yPos, this.width, viewPortHeight);

        toDraw.act(Gdx.graphics.getDeltaTime());
        toDraw.draw();
    }

    @Override
    public void resize(final int aWidth, final int aHeight) {
        this.width = aWidth;
        this.height = aHeight;

        riverLeft.resize(aWidth, aHeight);
        riverRight.resize(aWidth, aHeight);
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
        this.sound.stop();
    }

    /**
     * adds an obstacle on the..
     *
     * @param isRight - left or right side
     * @param graphic - where the obstacle is the graphic
     */
    public void addObstacle(final boolean isRight, final CannonBallGraphic graphic) {
        if (isRight) {
            this.riverRight.spawnObstacle(graphic);
        } else {
            this.riverLeft.spawnObstacle(graphic);
        }
    }

    /**
     * adds an rock on the..
     *
     * @param isRight - left or right side
     * @param graphic - where the rock is the graphic
     */
    public void addRock(final boolean isRight, final RockGraphic graphic) {
        if (isRight) {
            this.riverRight.spawnRock(graphic);
        } else {
            this.riverLeft.spawnRock(graphic);
        }
    }

    /**
     * Add a boat to the screen.
     *
     * @param team The team to add the boat to.
     */
    public void addBoat(final Team team) {
        BoatGroup boat = new BoatGroup(Gdx.graphics.getWidth() * BOAT_X_OFFSET,
                (Gdx.graphics.getHeight() * (float) RIVER_SIZE), team.getId());
        boat.init();

        if (team.getId() % 2 == 0) {
            this.riverRight.setBoat(boat);
        } else {
            this.riverLeft.setBoat(boat);
        }
        team.setBoat(boat);
    }

    /**
     * Update the progress.
     *
     * @param teamID   The team id
     * @param progress The progress
     * @param speed    the speed
     */
    public void updateProgress(final int teamID, final double progress, final double speed) {
        if (teamID % 2 == 0) {
            this.riverRight.getRiver().updateFlow(speed);
        } else {
            this.riverLeft.getRiver().updateFlow(speed);
        }

        this.betweenRivers.updateProgress(teamID, progress);
    }
}

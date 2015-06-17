package nl.tudelft.ti2806.riverrush.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import nl.tudelft.ti2806.riverrush.game.TickHandler;
import nl.tudelft.ti2806.riverrush.graphics.Assets;
import nl.tudelft.ti2806.riverrush.graphics.CenterStage;
import nl.tudelft.ti2806.riverrush.graphics.SideStage;
import nl.tudelft.ti2806.riverrush.graphics.entity.BoatGroup;
import nl.tudelft.ti2806.riverrush.graphics.entity.CannonBallGraphic;
import nl.tudelft.ti2806.riverrush.graphics.entity.RockGraphic;
import nl.tudelft.ti2806.riverrush.graphics.entity.Team;

/**
 * The playing game screen constructs and displays all the visuals that are required during game
 * play.
 */
public class PlayingGameScreen implements Screen {

    private static final double BANK_SIZE = 0.025;
    private static final double RIVER_SIZE = 0.45;
    private static final double MID_SIZE = 0.05;

    private SideStage riverLeft;
    private SideStage riverRight;
    private CenterStage betweenRivers;
    private Stage banksLeft;
    private Stage banksRight;

    private TickHandler onTick;

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

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.banksLeft = this.createRiverBank();
        this.banksRight = this.createRiverBank();
    }

    /**
     * Creates the river banks.
     *
     * @return A fresh river bank Stage.
     */
    private Stage createRiverBank() {

        Stage bank = new Stage();
        Image rightImg = new Image(Assets.riverBank);
        rightImg.setFillParent(true);
        bank.addActor(rightImg);
        return bank;
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        double startPosition = 0.0;

        this.drawStage(this.banksLeft, startPosition, BANK_SIZE);
        startPosition += BANK_SIZE;

        this.drawStage(this.riverLeft, startPosition, RIVER_SIZE);
        startPosition += RIVER_SIZE;

        this.drawStage(this.betweenRivers, startPosition, MID_SIZE);
        startPosition += MID_SIZE;

        this.drawStage(this.riverRight, startPosition, RIVER_SIZE);
        startPosition += RIVER_SIZE;

        this.drawStage(this.banksRight, startPosition, BANK_SIZE);

        this.onTick.handle();
    }

    /**
     * Draws a stage on the screen. Each stage occupies the entire height of the screen.
     *
     * @param toDraw             The stage to draw.
     * @param positionPercentage The position of the stage from the left. Between 0 and 1.
     * @param heigthPercentage   The width of the stage between 0 and 1. (1 = 100%).
     */
    private void drawStage(final Stage toDraw, final double positionPercentage,
                           final double heigthPercentage) {

        int yPos = (int) (Gdx.graphics.getHeight() * positionPercentage);
        int height = (int) (Gdx.graphics.getHeight() * heigthPercentage);
        Gdx.gl.glViewport(0, yPos, Gdx.graphics.getWidth(), height);

        toDraw.act(Gdx.graphics.getDeltaTime());
        toDraw.draw();
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
     * @param isLeft  - left or right side
     * @param graphic - where the obstacle is the graphic
     */
    public void addObstacle(final boolean isLeft, final CannonBallGraphic graphic) {
        if (isLeft) {
            this.riverLeft.spawnObstacle(graphic);
        } else {
            this.riverRight.spawnObstacle(graphic);
        }
    }

    /**
     * adds an rock on the..
     *
     * @param isLeft  - left or right side
     * @param graphic - where the rock is the graphic
     */
    public void addRock(final boolean isLeft, final RockGraphic graphic) {
        if (isLeft) {
            this.riverLeft.spawnRock(graphic);
        } else {
            this.riverRight.spawnRock(graphic);
        }
    }

    /**
     * Add a boat to the screen.
     *
     * @param team The team to add the boat to.
     */
    public void addBoat(final Team team) {
        BoatGroup boat = new BoatGroup(Gdx.graphics.getWidth() * 0.02f,
            (Gdx.graphics.getHeight() / 2) - 450, team.getId());
        boat.init();

        if (team.getId() % 2 == 0) { // TODO: Temporary hard coding
            this.riverLeft.addActor(boat);
        } else {
            this.riverRight.addActor(boat);
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
        // TODO Change river speed
        this.riverLeft.getRiver().updateFlow(speed);
        this.riverRight.getRiver().updateFlow(speed);
        this.betweenRivers.updateProgress(teamID, progress);
    }
}

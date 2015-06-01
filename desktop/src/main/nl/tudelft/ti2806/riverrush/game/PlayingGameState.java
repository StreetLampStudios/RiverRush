package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.GameState;
import nl.tudelft.ti2806.riverrush.domain.entity.Player;
import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.graphics.GdxGame;
import nl.tudelft.ti2806.riverrush.graphics.entity.ObstacleGraphic;
import nl.tudelft.ti2806.riverrush.screen.PlayingGameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;

/**
 * State for a game that is playing.
 */
public class PlayingGameState implements GameState {

    private final EventDispatcher dispatcher;
    private final AssetManager assets;
    private final GdxGame gameWindow;
    private final PlayingGameScreen screen;
    private final HandlerLambda<PlayerJumpedEvent> playerJumpedEventHandlerLambda = (e) -> this
            .jump(e.getPlayer());
    private final HandlerLambda<AddObstacleEvent> addObstacleEventHandlerLambda = (e) -> this
        .addObstacle(e);

    private final TickHandler OnTick = () -> this.tick();

    private final ArrayList<ObstacleGraphic> leftObstList;
    private final ArrayList<ObstacleGraphic> rightObstList;


    /**
     * The state of the game that indicates that the game is currently playable.
     *
     * @param eventDispatcher
     *            the dispatcher that is used to handle any relevant events for the game in this
     *            state.
     * @param assetManager
     *            has all necessary assets loaded and available for use.
     * @param game
     *            refers to the game that this state belongs to.
     */
    public PlayingGameState(final EventDispatcher eventDispatcher, final AssetManager assetManager,
            final GdxGame game) {
        this.gameWindow = game;
        this.assets = assetManager;
        this.dispatcher = eventDispatcher;
        this.dispatcher.attach(PlayerJumpedEvent.class, this.playerJumpedEventHandlerLambda);
        this.dispatcher.attach(AddObstacleEvent.class, this.addObstacleEventHandlerLambda);

        this.screen = new PlayingGameScreen(assetManager, eventDispatcher);
        Gdx.app.postRunnable(() -> {
            PlayingGameState.this.screen.init(OnTick);
            PlayingGameState.this.gameWindow.setScreen(PlayingGameState.this.screen);
        });

        this.leftObstList = new ArrayList<ObstacleGraphic>();
        this.rightObstList = new ArrayList<ObstacleGraphic>();

    }

    /**
     * Tells a given player to perform the jump action.
     *
     * @param player
     *            refers to the player character that has to jump.
     */
    public void jump(final Player player) {
        // this.screen.jump(player);
    }

    @Override
    public void dispose() {
        this.dispatcher.detach(PlayerJumpedEvent.class, this.playerJumpedEventHandlerLambda);
        this.screen.dispose();
    }

    @Override
    public GameState start() {
        return this;
    }

    @Override
    public GameState stop() {
        this.screen.dispose();
        return new StoppedGameState(this.dispatcher, this.assets, this.gameWindow);
    }

    @Override
    public GameState finish() {
        this.screen.dispose();
        return new FinishedGameState(this.dispatcher, this.assets, this.gameWindow);
    }

    @Override
    public GameState waitForPlayers() {
        return this;
    }

    /**
     * This method is called when the game renders the screen.
     */
    private void tick() {
        for (ObstacleGraphic graphic : leftObstList) {
            //FIXME add real teams
//            for (Player play : leftTeam) {
//                graphic.collide(play);
//            }
        }

        for (ObstacleGraphic graphic : rightObstList) {
            //FIXME add real teams
//            for (Player play : rightTeam) {
//                graphic.collide(play);
//            }
        }
    }

    /**
     * Is called when an obstacle event is received.
     * @param e - the event
     */
    private void addObstacle(final AddObstacleEvent e) {
        ObstacleGraphic graphic = new ObstacleGraphic(assets, e.getOffset());

        screen.addObstacle(e.isLeft(), graphic);
        if (e.isLeft()) {
            leftObstList.add(graphic);
        } else {
            rightObstList.add(graphic);
        }
    }
}

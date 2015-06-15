package nl.tudelft.ti2806.riverrush.game;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.state.FinishedGameState;
import nl.tudelft.ti2806.riverrush.game.state.PlayingGameState;
import nl.tudelft.ti2806.riverrush.game.state.StoppedGameState;
import nl.tudelft.ti2806.riverrush.game.state.WaitingForRendererState;
import nl.tudelft.ti2806.riverrush.game.state.WaitingGameState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for the main game workings.
 */
public class GameTest extends AbstractModule {

    private static final int ANIMAL_ID = 1;
    private static final int TEAM_ID = 1;
    private Game game;

    /**
     * The event dispatcher.
     */
    @Mock
    private EventDispatcher dispatcher;

    /**
     * The game track.
     */
    @Mock
    private GameTrack track;

    /**
     * The animal.
     */
    @Mock
    private Animal animal;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Injector injector = Guice.createInjector(this);

        this.game = injector.getInstance(Game.class);

        when(this.animal.getId()).thenReturn(ANIMAL_ID);
        when(this.animal.getTeamId()).thenReturn(TEAM_ID);
        when(this.track.addAnimal(TEAM_ID, this.animal)).thenReturn(TEAM_ID);

    }

    @Test
    public void testStart() throws Exception {
        assertTrue(this.game.getGameState() instanceof WaitingForRendererState);
        this.game.start();
        assertTrue(this.game.getGameState() instanceof WaitingForRendererState);
    }

    @Test
    public void testStop() throws Exception {
        assertTrue(this.game.getGameState() instanceof WaitingForRendererState);
        this.game.stop();
        assertTrue(this.game.getGameState() instanceof WaitingForRendererState);
    }

    @Test
    public void testFinish() throws Exception {
        assertTrue(this.game.getGameState() instanceof WaitingForRendererState);
        this.game.finish(0);
        assertTrue(this.game.getGameState() instanceof WaitingForRendererState);
    }

    @Test
    public void testWaitForPlayers() throws Exception {
        assertTrue(this.game.getGameState() instanceof WaitingForRendererState);
        this.game.waitForPlayers();
        assertTrue(this.game.getGameState() instanceof WaitingGameState);
    }

    @Test
    public void testWaitingStart() throws Exception {
        this.game.setGameState(new WaitingGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof WaitingGameState);
        this.game.start();
        assertTrue(this.game.getGameState() instanceof PlayingGameState);
    }

    @Test
    public void testWaitingStop() throws Exception {
        this.game.setGameState(new WaitingGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof WaitingGameState);
        this.game.stop();
        assertTrue(this.game.getGameState() instanceof StoppedGameState);
    }

    @Test
    public void testWaitingFinish() throws Exception {
        this.game.setGameState(new WaitingGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof WaitingGameState);
        this.game.finish(0);
        assertTrue(this.game.getGameState() instanceof WaitingGameState);
    }

    @Test
    public void testWaitingWaitingForPlayers() throws Exception {
        this.game.setGameState(new WaitingGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof WaitingGameState);
        this.game.waitForPlayers();
        assertTrue(this.game.getGameState() instanceof WaitingGameState);
    }

    @Test
    public void testStartedStart() throws Exception {
        this.game.setGameState(new PlayingGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof PlayingGameState);
        this.game.start();
        assertTrue(this.game.getGameState() instanceof PlayingGameState);
    }

    @Test
    public void testStartedStop() throws Exception {
        this.game.setGameState(new PlayingGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof PlayingGameState);
        this.game.stop();
        assertTrue(this.game.getGameState() instanceof StoppedGameState);
    }

    @Test
    public void testStartedFinish() throws Exception {
        this.game.setGameState(new PlayingGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof PlayingGameState);
        this.game.finish(0);
        assertTrue(this.game.getGameState() instanceof FinishedGameState);
    }

    @Test
    public void testStartedWaitingForPlayers() throws Exception {
        this.game.setGameState(new PlayingGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof PlayingGameState);
        this.game.waitForPlayers();
        assertTrue(this.game.getGameState() instanceof PlayingGameState);
    }

    @Test
    public void testFinishedStart() throws Exception {
        this.game.setGameState(new FinishedGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof FinishedGameState);
        this.game.start();
        assertTrue(this.game.getGameState() instanceof PlayingGameState);
    }

    @Test
    public void testFinishedStop() throws Exception {
        this.game.setGameState(new FinishedGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof FinishedGameState);
        this.game.stop();
        assertTrue(this.game.getGameState() instanceof StoppedGameState);
    }

    @Test
    public void testFinishedFinish() throws Exception {
        this.game.setGameState(new FinishedGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof FinishedGameState);
        this.game.finish(0);
        assertTrue(this.game.getGameState() instanceof FinishedGameState);
    }

    @Test
    public void testFinishedWaitingForPlayers() throws Exception {
        this.game.setGameState(new FinishedGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof FinishedGameState);
        this.game.waitForPlayers();
        assertTrue(this.game.getGameState() instanceof WaitingGameState);
    }

    @Test
    public void testStoppedAllActions() throws Exception {
        this.game.setGameState(new StoppedGameState(this.dispatcher, this.game));
        assertTrue(this.game.getGameState() instanceof StoppedGameState);
        this.game.start();
        assertTrue(this.game.getGameState() instanceof StoppedGameState);
        this.game.stop();
        assertTrue(this.game.getGameState() instanceof StoppedGameState);
        this.game.finish(0);
        assertTrue(this.game.getGameState() instanceof StoppedGameState);
        this.game.waitForPlayers();
        assertTrue(this.game.getGameState() instanceof StoppedGameState);
    }

    @Test
    public void testAddPlayerToTeam() throws Exception {
        this.game.addPlayerToTeam(this.animal, this.animal.getTeamId());
        verify(this.track, times(1)).addAnimalToTeam(this.animal, this.animal.getTeamId());
    }

    @Test
    public void testCollideAnimal() throws Exception {
        this.game.collideAnimal(this.animal.getId(), this.animal.getTeamId());
        verify(this.track, times(1)).collideAnimal(this.animal.getId(), this.animal.getTeamId());
    }

    @Override
    protected void configure() {
        this.bind(GameTrack.class).toInstance(this.track);
        this.bind(EventDispatcher.class).toInstance(this.dispatcher);
    }
}

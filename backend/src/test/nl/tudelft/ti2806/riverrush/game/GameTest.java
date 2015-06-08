package nl.tudelft.ti2806.riverrush.game;

import nl.tudelft.ti2806.riverrush.domain.entity.Animal;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.state.FinishedGameState;
import nl.tudelft.ti2806.riverrush.game.state.PlayingGameState;
import nl.tudelft.ti2806.riverrush.game.state.StoppedGameState;
import nl.tudelft.ti2806.riverrush.game.state.WaitingForRendererState;
import nl.tudelft.ti2806.riverrush.game.state.WaitingGameState;
import nl.tudelft.ti2806.riverrush.network.event.JumpCommand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for the main game workings.
 */
public class GameTest {

    public static final int animalId = 1;
    public static final int teamId = 1;
    public static final int WrongTeam = 10;
    private Game game;
    private EventDispatcher dispatcher;
    private Animal animal;

    @Before
    public void setUp() throws Exception {
        dispatcher = Mockito.mock(EventDispatcher.class);
        animal = Mockito.mock(Animal.class);
        game = new Game(dispatcher);

        when(this.animal.getId()).thenReturn(animalId);
        when(this.animal.getTeamId()).thenReturn(teamId);

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
        this.game.finish();
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
        this.game.finish();
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
        this.game.finish();
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
        assertTrue(this.game.getGameState() instanceof WaitingGameState);
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
        this.game.finish();
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
        this.game.finish();
        assertTrue(this.game.getGameState() instanceof StoppedGameState);
        this.game.waitForPlayers();
        assertTrue(this.game.getGameState() instanceof StoppedGameState);
    }

    @Test
    public void testAddPlayerToTeam() throws Exception {
        this.game.addPlayerToTeam(this.animal, this.animal.getTeamId());
    }

    @Test
    public void testJumpAnimal() throws Exception {
        this.game.jumpAnimal(this.animal);
        verify(this.animal, times(1)).jump();
    }

    @Test
    public void testCollideAnimal() throws Exception {
        this.game.addPlayerToTeam(this.animal, this.animal.getTeamId());
        this.game.collideAnimal(this.animal.getId(), this.animal.getTeamId());
        verify(this.animal, times(1)).collide();
    }
}

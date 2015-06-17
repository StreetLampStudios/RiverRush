package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.game.Game;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test for the playing game state.
 */
public class PlayingGameStateTest {

    @Mock
    private EventDispatcher dispatcher;

    @Mock
    private Game game;

    private PlayingGameState gamestate;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.gamestate = new PlayingGameState(this.dispatcher, this.game);

        verify(this.dispatcher, times(1)).dispatch(isA(GameStartedEvent.class));
    }

    @Test
    public void testStart() throws Exception {
        assertTrue(this.gamestate.start() instanceof PlayingGameState);
    }

    @Test
    public void testStop() throws Exception {
        assertTrue(this.gamestate.stop() instanceof StoppedGameState);
    }

    @Test
    public void testFinish() throws Exception {
        assertTrue(this.gamestate.finish(0) instanceof FinishedGameState);

        verify(this.dispatcher, times(1)).dispatch(isA(GameFinishedEvent.class));
    }

    @Test
    public void testWaitForPlayers() throws Exception {
        assertTrue(this.gamestate.waitForPlayers() instanceof PlayingGameState);
    }
}

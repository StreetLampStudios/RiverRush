package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.GameAboutToWaitEvent;
import nl.tudelft.ti2806.riverrush.game.Game;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test for the finished game state.
 */
public class FinishedGameStateTest {

    public static final int TIME_TO_WAIT = 5000;
    @Mock
    private EventDispatcher dispatcher;

    @Mock
    private Game game;

    private FinishedGameState gamestate;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.gamestate = new FinishedGameState(this.dispatcher, this.game);
    }

    @Test
    public void verifyGameAboutToWait() {
        ArgumentCaptor<GameAboutToWaitEvent> argument = ArgumentCaptor.forClass(GameAboutToWaitEvent.class);
        verify(this.dispatcher, times(1)).dispatch(argument.capture());
        assertEquals(TIME_TO_WAIT, argument.getValue().getTimeTillWait());
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
    }

    @Test
    public void testWaitForPlayers() throws Exception {
        assertTrue(this.gamestate.waitForPlayers() instanceof WaitingGameState);
    }
}

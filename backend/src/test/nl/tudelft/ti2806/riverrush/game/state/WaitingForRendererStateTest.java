package nl.tudelft.ti2806.riverrush.game.state;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;

/**
 * Test for waiting for renderer game state.
 */
public class WaitingForRendererStateTest {

    @Mock
    private EventDispatcher dispatcher;

    @Mock
    private Game game;

    private WaitingForRendererState gamestate;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.gamestate = new WaitingForRendererState(this.dispatcher, this.game);
    }

    @Test
    public void testStart() throws Exception {
        assertTrue(this.gamestate.start() instanceof WaitingForRendererState);
    }

    @Test
    public void testStop() throws Exception {
        assertTrue(this.gamestate.stop() instanceof WaitingForRendererState);
    }

    @Test
    public void testFinish() throws Exception {
        assertTrue(this.gamestate.finish(0) instanceof WaitingForRendererState);
    }

    @Test
    public void testWaitForPlayers() throws Exception {
        assertTrue(this.gamestate.waitForPlayers() instanceof WaitingGameState);
    }
}

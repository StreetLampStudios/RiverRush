package nl.tudelft.ti2806.riverrush.game.state;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test for {@link WaitingGameState}
 */
public class WaitingGameStateTest extends AbstractGameStateTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testStart() throws Exception {
        assertTrue(state.start() instanceof PlayingGameState);
    }

    @Test
    public void testStop() throws Exception {
        assertTrue(state.stop() instanceof StoppedGameState);
    }

    @Override
    public AbstractGameState getState() {
        return new WaitingGameState(dispatcher, game);
    }
}

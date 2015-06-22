package nl.tudelft.ti2806.riverrush.game.state;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test for {@link PlayingGameState}
 */
public class PlayingGameStateTest extends AbstractGameStateTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    public AbstractGameState getState() {
        return new PlayingGameState(dispatcher,game);
    }

    @Test
    public void testStop() throws Exception {
        assertTrue(state.stop() instanceof StoppedGameState);
    }

    @Test
    public void testFinish() throws Exception {
        assertTrue(state.finish(2) instanceof FinishedGameState);
    }

}

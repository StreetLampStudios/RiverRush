package nl.tudelft.ti2806.riverrush.game.state;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        assertEquals(StoppedGameState.class.getName(), state.stop().getClass().getName());
    }

    @Test
    public void testFinish() throws Exception {
        assertEquals(FinishedGameState.class.getName(), state.finish(2).getClass().getName());
    }

}

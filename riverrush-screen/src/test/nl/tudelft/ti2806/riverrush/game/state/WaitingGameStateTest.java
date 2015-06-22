package nl.tudelft.ti2806.riverrush.game.state;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martijn on 22-6-2015.
 */
public class WaitingGameStateTest extends AbstractGameStateTest {

    @Test
    public void testStart() throws Exception {
        assertEquals(PlayingGameState.class.getName(), state.start().getClass().getName());
    }

    @Test
    public void testStop() throws Exception {
        assertEquals(StoppedGameState.class.getName(), state.stop().getClass().getName());
    }

    @Override
    public AbstractGameState getState() {
        return new WaitingGameState(dispatcher, game);
    }
}

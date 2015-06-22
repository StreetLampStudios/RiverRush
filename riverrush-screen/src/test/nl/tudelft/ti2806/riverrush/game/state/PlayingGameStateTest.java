package nl.tudelft.ti2806.riverrush.game.state;

import com.badlogic.gdx.Screen;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.graphics.entity.Team;
import nl.tudelft.ti2806.riverrush.screen.PlayingGameScreen;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Test for {@link PlayingGameState}
 */
public class PlayingGameStateTest extends AbstractGameStateTest {
    private PlayingGameState pstate;
    private PlayingGameScreen screen;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        pstate = (PlayingGameState) state;
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

    @Test
    public void testAddAnimalHandler() throws Exception {

    }

    @Test
    public void testAnimalJumpHandler() throws Exception {

    }

    @Test
    public void testAnimalMoveHandler() throws Exception {

    }

    @Test
    public void testAnimalDropHandler() throws Exception {

    }
}

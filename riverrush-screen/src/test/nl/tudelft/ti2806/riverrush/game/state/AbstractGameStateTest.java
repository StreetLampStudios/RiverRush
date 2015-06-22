package nl.tudelft.ti2806.riverrush.game.state;

import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.Clipboard;
import nl.tudelft.ti2806.riverrush.desktop.MainDesktop;
import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Martijn on 22-6-2015.
 */
public abstract class AbstractGameStateTest {

    Game game;
    EventDispatcher dispatcher;
    AbstractGameState state;

    @Before
    public void setUp() throws Exception {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "RiverRush";

        game = mock(Game.class);
        when(game.getScreen()).thenCallRealMethod();
        doCallRealMethod().when(game).setScreen(any(Screen.class));

        dispatcher = mock(EventDispatcher.class);
        new LwjglApplication(game, config);

        state = getState();


    }

    @Test
    public void testStart() throws Exception {
        assertEquals(state, state.start());
    }

    @Test
    public void testStop() throws Exception {
        assertEquals(state, state.stop());
    }

    @Test
    public void testFinish() throws Exception {
        assertEquals(state, state.finish(5));
    }

    @Test
    public void testWaitForPlayers() throws Exception {
        assertEquals(state, state.waitForPlayers());
    }

    @Test
    public void testGetStateEvent() throws Exception {
        assertEquals(null, state.getStateEvent());
    }

    public abstract AbstractGameState getState();


}

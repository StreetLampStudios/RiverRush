package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.AnimalCollidedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.AssetsLoadedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.BasicEventDispatcher;
import nl.tudelft.ti2806.riverrush.domain.event.BoatCollidedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.Event;
import nl.tudelft.ti2806.riverrush.domain.event.GameFinishedEvent;
import nl.tudelft.ti2806.riverrush.domain.event.GameStartedEvent;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.Client;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests the RenderController.
 */
public class RenderControllerTest {

    private static ArrayList<Class> allEvents = new ArrayList<>();
    private RenderController ctrl;
    private BasicEventDispatcher dispatcher;
    private Game game;
    private Client client;

    @BeforeClass
    public static void beforeAll() {
        allEvents.add(GameStartedEvent.class);
        allEvents.add(GameFinishedEvent.class);
        allEvents.add(AssetsLoadedEvent.class);
        allEvents.add(AnimalCollidedEvent.class);
        allEvents.add(BoatCollidedEvent.class);
    }

    @Before
    public void before() {
        dispatcher = Mockito.spy(BasicEventDispatcher.class);
        game = Mockito.mock(Game.class);
        ctrl = new RenderController(game, dispatcher);
        verify(dispatcher, times(allEvents.size())).attach(Mockito.any(), Mockito.any());
        client = Mockito.mock(Client.class);
        ctrl.setClient(client);
    }

    @Test
    public void testOnSocketMessage() throws Exception {
        GameStartedEvent ev = new GameStartedEvent();
        ctrl.onSocketMessage(ev);
        verify(dispatcher).dispatch(ev);
    }

    @Test
    public void testDispose() throws Exception {
        ctrl.dispose();

        Mockito.verifyNoMoreInteractions(game);


        for (Class cls : allEvents) {
            dispatcher.dispatch((Event) cls.newInstance());
            System.out.println(cls.getName());

        }
    }
}

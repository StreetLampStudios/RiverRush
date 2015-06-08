package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.*;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.Client;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.*;

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
    }

    @Before
    public void before() {
        dispatcher = Mockito.spy(BasicEventDispatcher.class);
        game = Mockito.mock(Game.class);
        ctrl = new RenderController(game, dispatcher);
        Mockito.verify(dispatcher, Mockito.times(allEvents.size())).attach(Mockito.any(), Mockito.any());
        client = Mockito.mock(Client.class);
        ctrl.setClient(client);
    }

    @Test
    public void testOnSocketMessage() throws Exception {
        GameStartedEvent ev = new GameStartedEvent();
        ctrl.onSocketMessage(ev);
        Mockito.verify(dispatcher).dispatch(ev);
    }

    @Test
    public void testDispose() throws Exception {

        ctrl.dispose();

        Mockito.verifyNoMoreInteractions(game);


        for (Class cls : allEvents) {
            dispatcher.dispatch((Event) cls.newInstance());
            System.out.println(cls.getName());

        }

        Mockito.verify(this.dispatcher, Mockito.times(allEvents.size())).dispatch(Mockito.any());


        Mockito.verifyNoMoreInteractions(dispatcher);
    }
}

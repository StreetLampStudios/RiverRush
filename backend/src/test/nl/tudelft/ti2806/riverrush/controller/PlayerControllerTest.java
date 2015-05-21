package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.EventDispatcher;
import nl.tudelft.ti2806.riverrush.game.Game;
import nl.tudelft.ti2806.riverrush.network.UserServer;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PlayerControllerTest {

    @Mock
    private EventDispatcher dispatcherMock;

    @Mock
    private Game gameMock;

    @Mock
    private UserServer serverMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     *
     */

}

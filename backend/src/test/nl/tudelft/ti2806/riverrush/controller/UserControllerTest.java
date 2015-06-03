package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.AnimalAddedEvent;
import nl.tudelft.ti2806.riverrush.game.Game;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Conctrete test class for UserController.
 */
public class UserControllerTest extends ControllerTest {

    @Override
    public void setup() {
        super.setup();
        controller = new UserController(this.dispatcherMock, this.serverMock, mock(Game.class));
    }

    /**
     * When a UserController initialized,
     * it should send an event to the player
     * to indicate to the user that she joined the game.
     */
    @Test
    public void initialize_raisesPlayerAddedEvent() {
        controller.initialize();
        verify(this.serverMock).sendEvent(any(AnimalAddedEvent.class), same(controller));
    }
}

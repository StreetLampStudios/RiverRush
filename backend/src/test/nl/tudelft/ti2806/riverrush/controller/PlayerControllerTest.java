package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.domain.event.PlayerAddedEvent;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

/**
 * Conctrete test class for PlayerController.
 */
public class PlayerControllerTest extends ControllerTest {

    @Override
    public void setup() {
        super.setup();
        controller = new PlayerController(this.dispatcherMock, this.serverMock);
    }

    /**
     * When a PlayerController initialized,
     * it should send an event to the player
     * to indicate to the user that she joined the game.
     */
    @Test
    public void initialize_raisesPlayerAddedEvent() {
        controller.initialize();
        verify(this.serverMock).sendEvent(any(PlayerAddedEvent.class), same(controller));
    }
}

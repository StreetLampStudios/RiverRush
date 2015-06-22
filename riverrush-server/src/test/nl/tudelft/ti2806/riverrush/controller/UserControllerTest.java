package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.game.Game;

import static org.mockito.Mockito.mock;

/**
 * Conctrete test class for UserController.
 */
public class UserControllerTest extends ControllerTest {

    @Override
    public void setup() {
        super.setup();
        controller = new UserController(this.dispatcherMock, this.serverMock, mock(Game.class));
    }
}

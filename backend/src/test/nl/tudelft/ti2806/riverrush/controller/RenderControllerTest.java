package nl.tudelft.ti2806.riverrush.controller;

import nl.tudelft.ti2806.riverrush.game.Game;

import static org.mockito.Mockito.mock;

public class RenderControllerTest extends ControllerTest {

    @Override
    public void setup() {
        super.setup();
        controller = new RenderController(this.dispatcherMock, this.serverMock, mock(Game.class));
    }
}

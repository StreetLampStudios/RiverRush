package nl.tudelft.ti2806.riverrush.controller;

/**
 * Conctrete test class for PlayerController.
 */
public class PlayerControllerTest extends ControllerTest {

    @Override
    public void setup() {
        super.setup();
        controller =  new PlayerController(this.dispatcherMock, this.serverMock);
    }
}

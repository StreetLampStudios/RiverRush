package nl.tudelft.ti2806.riverrush.controller;

public class PlayerControllerTest extends ControllerTest {

    @Override
    public void setup() {
        super.setup();
        controller =  new PlayerController(this.dispatcherMock, this.serverMock);
    }
}

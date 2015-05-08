package nl.tudelft.ti2806.riverrush.backend;

import nl.tudelft.ti2806.riverrush.network.PlayerInteractionHandler;
import nl.tudelft.ti2806.riverrush.domain.entity.Command;
import nl.tudelft.ti2806.riverrush.failfast.NullException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for the PlayerInteractionHandler.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerInteractionHandlerTest {

    /**
     * To test handler interactions.
     * This mock is not configured to do anything.
     */
    @Mock private Command command;

    /**
     * Test whether a simple byte-sized message is recognized
     * when a MessageHandler is registered.
     */
    @Test
    public void isRegistered_yes_returnsTrue() {
        PlayerInteractionHandler handler = new PlayerInteractionHandler();

        String message = "jump";
        handler.onReceive(message, command);


        Assert.assertTrue(handler.isRegistered(message));
    }

    /**
     * Test whether a message is not recognized
     * when a MessageHandler is not registered.
     */
    @Test
    public void isRegistered_no_returnsFalse() {
        PlayerInteractionHandler handler = new PlayerInteractionHandler();

        String message = "jump";
        Assert.assertFalse(handler.isRegistered(message));
    }

    /**
     * Test whether registerCommand fails fast. (1)
     */
    @Test(expected = NullException.class)
    public void registerCommand_failsOnNull_1() {
        PlayerInteractionHandler handler = new PlayerInteractionHandler();

        handler.onReceive(null, command);
    }

    /**
     * Test whether registerCommand fails fast. (2)
     */
    @Test(expected = NullException.class)
    public void registerCommand_failsOnNull_2() {
        PlayerInteractionHandler handler = new PlayerInteractionHandler();

        String message = "jump";
        handler.onReceive(message, null);
    }

    /**
     * Test whether isRegistered fails fast.
     */
    @Test(expected = NullException.class)
    public void isRegistered_failsOnNull() {
        PlayerInteractionHandler handler = new PlayerInteractionHandler();
        handler.isRegistered(null);
    }
}

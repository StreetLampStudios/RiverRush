package nl.tudelft.ti2806.monkeyrush.backend;

import nl.tudelft.ti2806.monkeyrush.failfast.NullException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests for the PlayerCommandPublisher.
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerCommandHandlerTest {

    /**
     * To test handler interactions.
     * This mock is not configured to do anything.
     */
    @Mock private PlayerCommand command;

    /**
     * To test calls from handler to the game.
     */
    @Mock private PlayerCommandObserver game;

    /**
     * Test whether a simple byte-sized message is recognized
     * when a MessageHandler is registered.
     */
    @Test
    public void isRegistered_yes_returnsTrue() {
        PlayerCommandPublisher handler = new PlayerCommandPublisher();

        byte message = 0;
        handler.registerCommand(message, command);
        Assert.assertTrue(handler.isRegistered(message));
    }

    /**
     * Test whether a message is not recognized
     * when a MessageHandler is not registered.
     */
    @Test
    public void isRegistered_no_returnsFalse() {
        PlayerCommandPublisher handler = new PlayerCommandPublisher();

        byte message = 0;
        Assert.assertFalse(handler.isRegistered(message));
    }

    /**
     * Test whether registerCommand fails fast. (1)
     */
    @Test(expected = NullException.class)
    public void registerCommand_failsOnNull_1() {
        PlayerCommandPublisher handler = new PlayerCommandPublisher();

        handler.registerCommand(null, command);
    }

    /**
     * Test whether registerCommand fails fast. (2)
     */
    @Test(expected = NullException.class)
    public void registerCommand_failsOnNull_2() {
        PlayerCommandPublisher handler = new PlayerCommandPublisher();

        byte message = 0;
        handler.registerCommand(message, null);
    }

    /**
     * Test whether isRegistered fails fast.
     */
    @Test(expected = NullException.class)
    public void isRegistered_failsOnNull() {
        PlayerCommandPublisher handler = new PlayerCommandPublisher();
        handler.isRegistered(null);
    }
}
